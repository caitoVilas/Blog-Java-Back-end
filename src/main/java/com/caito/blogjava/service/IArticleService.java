package com.caito.blogjava.service;

import com.caito.blogjava.dto.ArticleResponse;
import com.caito.blogjava.dto.NewArticle;
import javassist.NotFoundException;

import java.util.List;

public interface IArticleService {
    public ArticleResponse createArticle(NewArticle newArticle);
    public ArticleResponse getArticle(Long id) throws NotFoundException;
    public List<ArticleResponse> getAllArticles();
    public void deleteArticle(Long id);
    public ArticleResponse updateArticle(Long id, NewArticle newArticle);
}