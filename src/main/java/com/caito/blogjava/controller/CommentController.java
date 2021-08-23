package com.caito.blogjava.controller;

import com.caito.blogjava.constatnts.ConstantsSwagger;
import com.caito.blogjava.dto.CommentNew;
import com.caito.blogjava.dto.CommentResponse;
import com.caito.blogjava.entity.Comment;
import com.caito.blogjava.service.implementation.CommentService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@CrossOrigin
@Api(value = ConstantsSwagger.MSG_SW_COMMENTS_API_VALUE, tags = ConstantsSwagger.MSG_SW_COMMENTS_API_TAG)
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    @ApiOperation(value = ConstantsSwagger.MSG_SW_COMMENTS_CREATE, response = CommentResponse.class)
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentNew commentNew){
        return new ResponseEntity<CommentResponse>(commentService.createComment(commentNew), HttpStatus.CREATED);
    }


    @GetMapping("/all/{article_id}")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_COMMENTS_LIST_ALL)
    public ResponseEntity<List<CommentResponse>> getAll(@PathVariable("article_id") Long article_id){
        return new ResponseEntity<List<CommentResponse>>(commentService.getComments(article_id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_COMMENTS_GETONE,  response = CommentResponse.class)
    public ResponseEntity<CommentResponse> getComment(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<CommentResponse>(commentService.getComment(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_COMMENTS_UPDATE, response = CommentResponse.class)
    public ResponseEntity<CommentResponse> updateComment(@PathVariable("id") Long id,
                                                         @RequestBody CommentNew commentNew){
        return new ResponseEntity<CommentResponse>(commentService.updateComment(id,commentNew), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_COMMENTS_DELETE)
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) throws NotFoundException {
        commentService.deleteComment(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/pageable")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_COMMENTS_LIST_PAGEABLE)
    public ResponseEntity<Page<Comment>> getAll(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String order,
                                                @RequestParam(defaultValue = "false") boolean asc){
        Page<Comment> comments = commentService.vewAllComments(PageRequest.of(
                page,
                size,
                Sort.by(order)
        ));
        if(!asc){
            comments = commentService.vewAllComments(PageRequest.of(
                    page,
                    size,
                    Sort.by(order).descending()
            ));
        }

        return new ResponseEntity<Page<Comment>>(comments, HttpStatus.OK);
    }
}
