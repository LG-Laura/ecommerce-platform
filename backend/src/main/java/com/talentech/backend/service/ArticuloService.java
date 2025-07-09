package com.talentech.backend.service;

import com.talentech.backend.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article>getAllArticles();
    Optional<Article>getArticleById(Long id);
    Article saveArticle(Article article);
    Article updateArticle(Long id, Article article);
    void deleteArticle(Long id);
}

