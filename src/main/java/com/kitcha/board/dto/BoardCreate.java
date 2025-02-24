package com.kitcha.board.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kitcha.board.entity.Board;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class BoardCreate {
    private Long boardId;
    private String boardTitle;
    private String content;
    private String newsTitle;
    private String summary;
    private String url;

    public Board toEntity(Long userId, String nickname) {
        return new Board(boardId, nickname, boardTitle, content, newsTitle, summary, url, userId);
    }
}
