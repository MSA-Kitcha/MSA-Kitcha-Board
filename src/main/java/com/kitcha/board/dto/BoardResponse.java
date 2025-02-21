package com.kitcha.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardResponse {
    private String boardTitle;
    private int hitCnt;
    private String writer; // userId를 키로 받아낸 nickname
    private String boardDate;
    private String content;
    private String longSummary;
    private String newsUrl;

    // TODO 파일 다운로드
}