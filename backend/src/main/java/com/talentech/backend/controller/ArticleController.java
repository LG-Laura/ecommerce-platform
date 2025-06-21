package com.talentech.backend.controller;


import com.talentech.backend.model.Article;
import com.talentech.backend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Expone métodos como API REST
@RequestMapping("/api/articles") // Ruta base
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getAll() {
        return articleService.getAllArticles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getById(@PathVariable Long id) {
        return articleService.getArticleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Article create(@RequestBody Article article) {
        return articleService.saveArticle(article);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody Article article) {
        if (articleService.getArticleById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articleService.updateArticle(id, article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (articleService.getArticleById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
