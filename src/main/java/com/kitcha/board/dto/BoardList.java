package com.kitcha.board.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
public class BoardList {
    private Long boardId;
    private String boardTitle;
    private int hitCnt;
    private String writer;
    private String boardDate;
}
