package com.mau.mau_project.domain.article.dto;

import lombok.Getter;

@Getter
public class WriteArticleDto {

    private Long boardId;
    private String title;
    private String content;
}
