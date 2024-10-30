package com.mau.mau_project.domain.article.service;

import com.mau.mau_project.db.article.ArticleEntity;
import com.mau.mau_project.db.article.ArticleRepository;
import com.mau.mau_project.db.board.BoardEntity;
import com.mau.mau_project.db.board.BoardRepository;
import com.mau.mau_project.db.user.UserEntity;
import com.mau.mau_project.db.user.UserRepository;
import com.mau.mau_project.domain.article.dto.WriteArticleDto;
import com.mau.mau_project.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public ArticleEntity writeArticle(WriteArticleDto writeArticleDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserEntity> author = userRepository.findByUserName(userDetails.getUsername());
        Optional<BoardEntity> board = boardRepository.findById(writeArticleDto.getBoardId());
        if (board.isEmpty()){
            throw new ResourceNotFoundException("board not found");
        }
        if (author.isEmpty()){
            throw new ResourceNotFoundException("author not found");
        }

        ArticleEntity articleEntity = ArticleEntity.builder()
                .board(board.get())
                .author(author.get())
                .title(writeArticleDto.getTitle())
                .content(writeArticleDto.getContent())
                .build();
        articleRepository.save(articleEntity);
        return articleEntity;

    }
}