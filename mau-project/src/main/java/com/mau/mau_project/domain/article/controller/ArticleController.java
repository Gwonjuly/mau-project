package com.mau.mau_project.domain.article.controller;

import com.mau.mau_project.db.article.ArticleEntity;
import com.mau.mau_project.domain.article.dto.WriteArticleDto;
import com.mau.mau_project.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{boardId}/articles")
    public ResponseEntity<List<ArticleEntity>> getArticles(@PathVariable("boardId") Long boardId,
                                                           @RequestParam(value = "lastId", required = false) Long lastId,
                                                           @RequestParam(value = "firstId", required = false) Long firstId ) {
        if (lastId != null) {
            return ResponseEntity.ok(articleService.getPreArticle(boardId,lastId));
        }
        if(firstId != null){
            return ResponseEntity.ok(articleService.getNextArticle(boardId,firstId));
        }
        return ResponseEntity.ok(articleService.getArticle(boardId));
    }
}
