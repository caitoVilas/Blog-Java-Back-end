package com.caito.blogjava.service.implementation;

import com.caito.blogjava.constatnts.ConstantExeptionMessages;
import com.caito.blogjava.dto.ArticleResponse;
import com.caito.blogjava.dto.NewArticle;
import com.caito.blogjava.dto.UserResponse;
import com.caito.blogjava.entity.Article;
import com.caito.blogjava.exceptions.customs.BadRequestException;
import com.caito.blogjava.repository.ArticleRepository;
import com.caito.blogjava.service.IArticleService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class ArticleService implements IArticleService {
    @Autowired
    private ArticleRepository articleRepository;


    @Override
    public ArticleResponse createArticle(NewArticle newArticle) {
        if (newArticle.getTitle() == null || newArticle.getTitle() == ""){
            throw new BadRequestException(ConstantExeptionMessages.MSG_ARTICLE_TITLE_EMPTY);
        }
        if (newArticle.getContent() == null || newArticle.getContent() == ""){
            throw new BadRequestException(ConstantExeptionMessages.MSG_ARTICLE_CONTENT_EMPTY);
        }
        ModelMapper mapper = new ModelMapper();
        articleRepository.save(mapper.map(newArticle, Article.class));
        return mapper.map(newArticle, ArticleResponse.class);
    }

    @Override
    public ArticleResponse getArticle(Long id) throws NotFoundException {
        Article article = articleRepository.findById(id).orElseThrow(()-> new NotFoundException(
                ConstantExeptionMessages.MSG_ARTICLE_NOT_FOUND));
        ModelMapper mapper = new ModelMapper();
        UserResponse userResponse = mapper.map(article.getUser(), UserResponse.class);
        ArticleResponse response = new ArticleResponse(article.getId(),article.getTitle(),userResponse,article.getContent());
        return response;
    }

    @Override
    public List<ArticleResponse> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        List<ArticleResponse> response = Arrays.asList(mapper.map(articles, ArticleResponse[].class));
        return response;
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(()-> new BadRequestException(
                ConstantExeptionMessages.MSG_ARTICLE_NOT_FOUND));
        articleRepository.deleteById(id);
    }

    @Override
    public ArticleResponse updateArticle(Long id, NewArticle newArticle) {
        Article article = articleRepository.findById(id).orElseThrow(()-> new BadRequestException(
                ConstantExeptionMessages.MSG_ARTICLE_NOT_FOUND));
        if (newArticle.getTitle() != null || newArticle.getTitle() != ""){
            article.setTitle(newArticle.getTitle());
        }
        if (newArticle.getContent() != null || newArticle.getContent() != ""){
            article.setContent(newArticle.getContent());
        }
        ModelMapper mapper = new ModelMapper();
        articleRepository.save(article);
        return mapper.map(article, ArticleResponse.class);
    }

}
