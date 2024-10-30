package com.mau.mau_project.domain.article.controller;

import com.mau.mau_project.db.article.ArticleEntity;
import com.mau.mau_project.domain.article.dto.WriteArticleDto;
import com.mau.mau_project.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final AuthenticationManager authenticationManager;

    @PostMapping ("/{boardId}/articles")
    public ResponseEntity<ArticleEntity> writeArticle(@RequestBody WriteArticleDto writeArticleDto) {
        return ResponseEntity.ok(articleService.writeArticle(writeArticleDto));
    }
}
