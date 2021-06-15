package com.caito.blogjava.service;

import com.caito.blogjava.dto.CommentNew;
import com.caito.blogjava.dto.CommentResponse;
import com.caito.blogjava.entity.Comment;
import javassist.NotFoundException;

import java.util.List;


public interface ICommentService {
    public CommentResponse createComment( CommentNew commentNew);
    public List<CommentResponse> getComments(Long article_id);
    public CommentResponse getComment(Long id) throws NotFoundException;
    public CommentResponse updateComment(Long id, CommentNew commentNew);
    public void deleteComment(Long id) throws NotFoundException;
}
