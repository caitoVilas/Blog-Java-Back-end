package com.caito.blogjava.service;

import com.caito.blogjava.dto.ArticleResponse;
import com.caito.blogjava.dto.NewArticle;
import com.caito.blogjava.entity.Article;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IArticleService {
    public ArticleResponse createArticle(NewArticle newArticle) throws IOException;
    public ArticleResponse getArticle(Long id) throws NotFoundException;
    public List<ArticleResponse> getAllArticles();
    public void deleteArticle(Long id) throws IOException;
    public ArticleResponse updateArticle(Long id, NewArticle newArticle);
    public ArticleResponse uploadImage(MultipartFile file, Long id) throws NotFoundException, IOException;
    public Page<Article> GetAllPaginator(Pageable pageable);
}