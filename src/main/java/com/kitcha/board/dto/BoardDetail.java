package com.kitcha.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardDetail {
    private Long boardId;
    private String boardTitle;
    private int hitCnt;
    private String writer;
    private String boardDate;
    private String content;
    private String longSummary;
    private String newsUrl;

    private boolean isOwner = false; // 작성자 본인인지?
    private boolean isAdmin = false; // 관리자인지?
}