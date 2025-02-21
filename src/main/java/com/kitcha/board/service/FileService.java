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
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
public class FileService {
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
        InputStream titleFontStream = getClass().getResourceAsStream("/fonts/HMKMRHD.ttf");
        PDType0Font titleFont = PDType0Font.load(document, titleFontStream);
        InputStream contentFontStream = getClass().getResourceAsStream("/fonts/NanumGothicCoding.ttf");
        PDType0Font contentCont = PDType0Font.load(document, contentFontStream);

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
            contentStream.showText(board.getNewsTitle());
            contentStream.endText();

            // 내용 추가 (기본 글꼴, 크기 12)
            contentStream.beginText();
            contentStream.setFont(contentCont, 12);
            contentStream.newLineAtOffset(50, pageHeight - 250); // x, y 좌표 (페이지에서 위치)
            for (String line : lines) {
                contentStream.showText(line);  // 한 줄 출력
                yPosition -= 15;               // Y 좌표 변경 (한 줄 아래로 이동)
                contentStream.newLineAtOffset(0, -15);
            }
            contentStream.endText();

            // 콘텐츠 스트림 닫기
            contentStream.close();

            // PDF 파일 저장
            String folderPath = "c:\\Temp\\kitcha\\";
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
        Optional<File> file = fileRepository.findById(boardId);

        return (file.isEmpty()) ?
                null :
                file;
    }
}
