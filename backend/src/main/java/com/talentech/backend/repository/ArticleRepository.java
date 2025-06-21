package com.talentech.backend.repository;

import com.talentech.backend.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // Buscar artículos por nombre exacto
    List<Article> findByName(String name);

    // Buscar artículos cuyo nombre contenga una palabra (LIKE '%texto%')
    List<Article> findByNameContaining(String text);

    // Buscar artículos con precio mayor a un valor dado
    List<Article> findByPriceGreaterThan(Double price);

    // Buscar artículos con precio entre dos valores
    List<Article> findByPriceBetween(Double min, Double max);

    // Buscar por nombre ignorando mayúsculas y minúsculas
    List<Article> findByNameIgnoreCase(String name);

    // Buscar artículos ordenados por precio ascendente
    List<Article> findAllByOrderByPriceAsc();

    // Buscar artículos por nombre y precio mayor a cierto valor
    List<Article> findByNameAndPriceGreaterThan(String name, Double price);
}

