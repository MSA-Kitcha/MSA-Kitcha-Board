package com.kitcha.board.entity;

import com.kitcha.board.dto.BoardDetail;
import com.kitcha.board.dto.BoardList;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Data
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column
    private String nickname;

    @Column(nullable = false)
    private String boardTitle;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int hitCnt = 0;

    @Column(nullable = false)
    private String newsTitle;

    @Column(nullable = false, length = 3000)
    private String longSummary;

    @Column(nullable = false)
    private String newsUrl;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean deletedYn = false;

    @Column
    private Long userId;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "file_id") // file 테이블의 file_id를 FK로 참조
//    private File file;

    public Board(Long boardId, String nickname, String boardTitle, String content, String newsTitle, String longSummary, String newsUrl, Long userId) {
        this.boardId = boardId;
        this.nickname = nickname;
        this.boardTitle = boardTitle;
        this.content = content;
        this.newsTitle = newsTitle;
        this.longSummary = longSummary;
        this.newsUrl = newsUrl;
        this.userId = userId;
    }

    public void updateHitCnt() {
        hitCnt++;
    }

    public BoardDetail toDetail(boolean isOwner, boolean isAdmin) {
        return new BoardDetail(boardId, boardTitle, hitCnt, nickname, createdAt.toString(), content, longSummary, newsUrl, isOwner, isAdmin);
    }

    public BoardList toList() {
        return new BoardList(boardId, boardTitle, hitCnt, nickname, createdAt.toString());
    }

    public void update(String boardTitle, String content) {
        this.boardTitle = boardTitle;
        this.content = content;
    }
}
