package com.kitcha.board.service;

import com.kitcha.board.dto.BoardCreate;
import com.kitcha.board.dto.BoardDetail;
import com.kitcha.board.dto.BoardList;
import com.kitcha.board.dto.BoardUpdate;
import com.kitcha.board.entity.Board;
import com.kitcha.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private FileService fileService;

    // 1. 게시글 작성
    public Board create(Long userId, String nickname, BoardCreate boardCreate) throws IOException {
        // boardId가 들어오는 경우 -> 잘못된 요청
        if (boardCreate.getBoardId() != null) {
            return null;
        }

        Board board = boardCreate.toEntity(userId, nickname);

        fileService.createPdf(board);

        return boardRepository.save(board);
    }


    // 2. 목록 조회
    public List<BoardList> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Board> boardPage = boardRepository.findByDeletedYnFalse(pageable);

        return boardPage.getContent().stream()
                .map(Board ->Board.toList()) // 엔티티를 DTO로 변환 //
                .collect(Collectors.toList());
    }

    // 3. 상세 조회
    public BoardDetail detail(Long userId, String role, Long boardId) {
        Optional<Board> optional = boardRepository.findById(boardId);

        // boardId에 해당하는 데이터가 없는 경우 -> 잘못된 요청
        if (optional.isEmpty()) {
            return null;
        }

        Board board = optional.get();
        
        // 삭제된 게시글인 경우 -> 잘못된 요청
        if (board.isDeletedYn()) {
            return null;
        }
        
        board.updateHitCnt();
        boardRepository.save(board);

        // 작성자 본인인가?
        boolean isOwner = board.getUserId().equals(userId);
        // 관리자인가?
        boolean isAdmin = role != null && role.equals("ADMIN");

        return board.toDetail(isOwner, isAdmin);
    }

    // 4. 수정
    public void update(Long boardId, Long userId, BoardUpdate boardUpdate) {
        Optional<Board> optional = boardRepository.findById(boardId);

        // boardId에 해당하는 데이터가 없는 경우 -> 잘못된 요청
        if (optional.isEmpty()) {
            return;
        }

        Board board = optional.get();

        // 삭제된 게시글인 경우 -> 잘못된 요청
        if (board.isDeletedYn()) {
            return;
        }

        // 작성자 본인이 아닌 경우 -> 권한 없음
        if (!board.getUserId().equals(userId)) {
            return;
        }

        board.update(boardUpdate.getBoardTitle(), boardUpdate.getContent());
        boardRepository.save(board);
    }

    // 5. 삭제
    public void delete(Long boardId, Long userId, String role) {
        Optional<Board> optional = boardRepository.findById(boardId);

        // boardId에 해당하는 데이터가 없는 경우 -> 잘못된 요청
        if (optional.isEmpty()) {
            return;
        }

        Board board = optional.get();

        // 작성자 본인인 경우 || 관리자인 경우 -> 삭제 가능
        if (board.getUserId().equals(userId) || role.equals("ADMIN")) {
            board.setDeletedYn(true);
            boardRepository.save(board);
        }
    }
}
