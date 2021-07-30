package com.caito.blogjava.controller;

import com.caito.blogjava.constatnts.ConstantsSwagger;
import com.caito.blogjava.dto.ArticleResponse;
import com.caito.blogjava.dto.NewArticle;
import com.caito.blogjava.entity.Article;
import com.caito.blogjava.service.implementation.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@CrossOrigin
@Api(value = ConstantsSwagger.MSG_SW_ARTICLES_API_VALUE, tags = ConstantsSwagger.MSG_SW_ARTICLES_API_TAG)
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    @ApiOperation(value = ConstantsSwagger.MSG_SW_ARTICLES_CREATE, response = ArticleResponse.class)
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody NewArticle newArticle) throws IOException {
        return new ResponseEntity<ArticleResponse>(articleService.createArticle(newArticle), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_ARTICLES_GETONE, response = ArticleResponse.class)
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<ArticleResponse>(articleService.getArticle(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_ARTICLES_LIST_ALL)
    public ResponseEntity<List<ArticleResponse>> getAllArticles(){
        return new ResponseEntity<List<ArticleResponse>>(articleService.getAllArticles(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_ARTICLES_DELETE)
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Long id) throws IOException {
        articleService.deleteArticle(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_ARTICLES_UPDATE, response = ArticleResponse.class)
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable("id") Long id,
                                                         @RequestBody NewArticle newArticle){
        return new ResponseEntity<ArticleResponse>(articleService.updateArticle(id, newArticle), HttpStatus.OK);
    }

    @PostMapping("/up-image")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_ARTICLES_UPLOAD_IMAGE)
    public ResponseEntity<ArticleResponse> uploadImage(@RequestParam MultipartFile file,
                                                       @RequestParam Long id) throws NotFoundException, IOException {
        return new ResponseEntity<ArticleResponse>(articleService.uploadImage(file, id), HttpStatus.OK);
    }

    @GetMapping("/pageable")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_ARTICLES_LIST_PAGEABLE)
    public ResponseEntity<Page<Article>> getAllrticlesPageable(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "id") String order,
                @RequestParam(defaultValue = "false") boolean asc ){


      Page<Article> articles = articleService.GetAllPaginator(PageRequest.of(
              page,
              size,
              Sort.by(order)
      ));
      if(!asc){
          articles = articleService.GetAllPaginator(PageRequest.of(
                  page,
                  size,
                  Sort.by(order).descending()
          ));
      }
      return new ResponseEntity<Page<Article>>(articles, HttpStatus.OK);
    }
}
