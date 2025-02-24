package com.kitcha.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardList {
    private Long boardId;
    private String boardTitle;
    private int hitCnt;
    private String writer;
    private String boardDate;
}
