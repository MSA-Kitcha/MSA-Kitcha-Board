package com.kitcha.board.controller;

import com.kitcha.board.dto.BoardCreate;
import com.kitcha.board.dto.BoardResponse;
import com.kitcha.board.dto.BoardUpdate;
import com.kitcha.board.entity.Board;
import com.kitcha.board.entity.File;
import com.kitcha.board.service.BoardService;
import com.kitcha.board.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apps/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private FileService fileService;

    // 1. 게시글 작성
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody BoardCreate boardCreate) throws IOException {
        Board newBoard = boardService.create(boardCreate);

        return (newBoard != null) ?
                ResponseEntity.ok().body(newBoard) :
                ResponseEntity.badRequest().build();
    }

    // 2. 목록 조회
    @GetMapping
    public ResponseEntity<List<BoardResponse>> list(@RequestParam int page, @RequestParam int size) {
        List<BoardResponse> results = boardService.list(page, size);

        return ResponseEntity.ok().body(results);
    }

    // 3. 상세 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> detail(@PathVariable Long boardId) {
        BoardResponse boardResponse = boardService.detail(boardId);

        return (boardResponse != null) ?
                ResponseEntity.ok().body(boardResponse) :
                ResponseEntity.badRequest().build();
    }

    // 4. 수정
    @PutMapping("/{boardId}")
    public void update(@PathVariable Long boardId, @RequestBody BoardUpdate boardUpdate) {
        boardService.update(boardId, boardUpdate);
    }

    // 5. 삭제
    @DeleteMapping("/{boardId}")
    public void delete(@PathVariable Long boardId) {
        boardService.delete(boardId);
    }

    // 6. 첨부파일 다운로드
    @GetMapping("/{boardId}/download")
    public void download(@PathVariable Long boardId,
                         HttpServletResponse response) throws IOException {
        Optional<File> optional = fileService.download(boardId);

        if (optional.isEmpty()) {
            return;
        }

        File file = optional.get();

        Path path = Paths.get(file.getFilePath());
        byte[] result = Files.readAllBytes(path);

        response.setContentType("application/octet-stream");
        response.setContentLength(result.length);
        response.setHeader("Content-Disposition",
                "attachment; fileName=\"" + URLEncoder.encode(file.getFileName() + ".pdf", "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(result);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
