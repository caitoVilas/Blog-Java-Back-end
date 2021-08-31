package com.caito.blogjava.repository;

import com.caito.blogjava.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    public Page<Article> findAllByTitleContains(String search, Pageable pageable);
}
