package com.mau.mau_project.db.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    @Query("SELECT a FROM ArticleEntity a where a.board.id = :boardId ORDER BY a.createdDate DESC")
    List<ArticleEntity> findTop10ByBoardIdOrderByCreatedDateDesc(@Param("boardId") Long boardId);

    @Query("SELECT a FROM ArticleEntity a where a.board.id =:boardId AND a.id < :articleId order by a.createdDate DESC")
    List<ArticleEntity> findTop10ByBoardIdAndArticleIdLessThanOrderByCreatedDateDesc(@Param("boardId") Long boardId, @Param("articleId") Long articleId);

    @Query("SELECT a FROM ArticleEntity a where a.board.id =:boardId AND a.id > :articleId order by a.createdDate DESC")
    List<ArticleEntity> findTop10ByBoardIdAndArticleIdGreaterThanOrderByCreatedDateDesc(@Param("boardId") Long boardId, @Param("articleId") Long articleId);
}
