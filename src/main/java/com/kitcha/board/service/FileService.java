package com.kitcha.board.service;

import com.kitcha.board.entity.Board;
import com.kitcha.board.entity.File;
import com.kitcha.board.repository.BoardRepository;
import com.kitcha.board.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class FileService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private FileRepository fileRepository;

    @Async("taskExecutor")
    public void createPdf(Board board) throws IOException {
        // PDF 문서 생성
        PDDocument document = new PDDocument();

        // 새로운 페이지 추가
        PDPage page = new PDPage();
        document.addPage(page);

        // 트루타입 폰트 로드
        InputStream titleFontStream = getClass().getResourceAsStream("/fonts/NotoSansKR-ExtraBold.ttf");
        PDType0Font titleFont = PDType0Font.load(document, titleFontStream);
        InputStream contentFontStream = getClass().getResourceAsStream("/fonts/NotoSansKR-Regular.ttf");
        PDType0Font contentFont = PDType0Font.load(document, contentFontStream);

        // 배너 이미지 로드
        InputStream inputStream = getClass().getResourceAsStream("/images/Background.png");
        PDImageXObject image = PDImageXObject.createFromByteArray(document, inputStream.readAllBytes(), "Background.png");

        // 이미지 크기 계산
        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight();

        // 내용 문단 처리
        String content = board.getLongSummary();
        String[] lines = content.split("\n");
        float yPosition = 650;

        try {
            // 콘텐츠 스트림 생성
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // 배경 추가
            contentStream.drawImage(image, 0, 0, pageWidth, pageHeight);

            // 제목 추가 (기본 글꼴, 크기 18)
            contentStream.beginText();
            contentStream.setFont(titleFont, 18);
            contentStream.newLineAtOffset(50, pageHeight - 200); // x, y 좌표 (페이지에서 위치)
//            contentStream.showText(board.getNewsTitle());

            float maxWidth = pageWidth - 100; // 좌우 여백 고려한 최대 폭
            List<String> wrappedLines = wrapText(board.getNewsTitle(), titleFont, 18, maxWidth);

            for (String line : wrappedLines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -26);
            }

            contentStream.endText();

            // 내용 추가 (기본 글꼴, 크기 12)
            contentStream.beginText();
            contentStream.setFont(contentFont, 12);
            contentStream.newLineAtOffset(50, pageHeight - 250); // x, y 좌표 (페이지에서 위치)

            //float maxWidth = pageWidth - 100; // 좌우 여백 고려한 최대 폭
            wrappedLines = wrapText(board.getLongSummary(), contentFont, 12, maxWidth);

            for (String line : wrappedLines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -20);
            }

            contentStream.endText();

            // 콘텐츠 스트림 닫기
            contentStream.close();

            // PDF 파일 저장
            String folderPath = System.getProperty("os.name").toLowerCase().contains("win")
                    ? "c:\\Temp\\kitcha\\"
                    : "/tmp/kitcha/";

            try {
                Path path = Paths.get(folderPath);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
            } catch (IOException e) {
                log.error("폴더 생성 실패: " + e.getMessage());
            }

            String fileName = board.getNewsTitle().replaceAll("\\s+", "_") + ".pdf";
            String fullPath = folderPath + fileName;
            document.save(fullPath);

            // 문서 닫기
            document.close();

            File file = new File(board.getBoardId(), board.getNewsTitle(), fullPath);
            fileRepository.save(file);

        } catch (IOException e) {
            e.printStackTrace();
            log.error("FileService.makePdf() : PDF 파일 생성 오류");
        }
    }

    // 6. 파일 다운로드
    public Optional<File> download(Long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        Optional<File> file = fileRepository.findById(boardId);

        // boardId에 해당하는 데이터가 없는 경우 -> 잘못된 요청
        if (board.isEmpty()) {
            return Optional.empty();
        }

        // 삭제된 게시글인 경우 -> 잘못된 요청
        if (board.get().isDeletedYn()) {
            return Optional.empty();
        }

        return file; // 파일이 있으면 파일 반환, 없으면 Optional.empty() 반환
    }

    // 줄바꿈 계산 함수
    public List<String> wrapText(String text, PDFont font, int fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        float currentWidth = 0;

        for (String word : text.split(" ")) {  // 단어 단위로 분리
            float wordWidth = font.getStringWidth(word) / 1000 * fontSize;  // 단어 폭 계산

            if (currentWidth + wordWidth > maxWidth) {  // 줄 길이 초과 시 줄바꿈
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
                currentWidth = wordWidth;
            } else {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                    currentWidth += font.getStringWidth(" ") / 1000 * fontSize;
                }
                currentLine.append(word);
                currentWidth += wordWidth;
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

}
