package com.caito.blogjava.controller;

import com.caito.blogjava.dto.ArticleResponse;
import com.caito.blogjava.dto.NewArticle;
import com.caito.blogjava.service.implementation.ArticleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody NewArticle newArticle) throws IOException {
        return new ResponseEntity<ArticleResponse>(articleService.createArticle(newArticle), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<ArticleResponse>(articleService.getArticle(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getAllArticles(){
        return new ResponseEntity<List<ArticleResponse>>(articleService.getAllArticles(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Long id){
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable("id") Long id,
                                                         @RequestBody NewArticle newArticle){
        return new ResponseEntity<ArticleResponse>(articleService.updateArticle(id, newArticle), HttpStatus.OK);
    }

    @PostMapping("/up-image")
    public ResponseEntity<ArticleResponse> uploadImage(@RequestParam MultipartFile file,
                                                       @RequestParam Long id) throws NotFoundException, IOException {
        return new ResponseEntity<ArticleResponse>(articleService.uploadImage(file, id), HttpStatus.OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<String> getAllrticlesPageable(@PageableDefault(size = 10, page = 0)Pageable pageable){
        return new ResponseEntity<String>(articleService.GetAllPaginator(pageable), HttpStatus.OK);
    }
}
