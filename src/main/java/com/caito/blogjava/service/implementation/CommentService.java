package com.caito.blogjava.service.implementation;

import com.caito.blogjava.constatnts.ConstantExeptionMessages;
import com.caito.blogjava.dto.ArticleResponse;
import com.caito.blogjava.dto.CommentNew;
import com.caito.blogjava.dto.CommentResponse;
import com.caito.blogjava.dto.UserResponse;
import com.caito.blogjava.entity.Article;
import com.caito.blogjava.entity.Comment;
import com.caito.blogjava.entity.User;
import com.caito.blogjava.exceptions.customs.BadRequestException;
import com.caito.blogjava.repository.ArticleRepository;
import com.caito.blogjava.repository.CommentRepository;
import com.caito.blogjava.repository.UserRepository;
import com.caito.blogjava.service.ICommentService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class CommentService implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public CommentResponse createComment(CommentNew commentNew) {
        Article article = articleRepository.findById(commentNew.getArticle_id()).orElseThrow(()->
                new BadRequestException(ConstantExeptionMessages.MSG_ARTICLE_NOT_FOUND));
        User user = userRepository.findById(commentNew.getUser_id()).orElseThrow(()->
                new BadRequestException(ConstantExeptionMessages.MSG_USER_NOT_FOUND));
        if (commentNew.getContent() == null || commentNew.getContent() == ""){
            throw new BadRequestException(ConstantExeptionMessages.MSG_COMMENT_EMPTY_CONTENT);
        }

        Comment comment = new Comment(null,user,commentNew.getContent(),article, LocalDateTime.now(),
                LocalDateTime.now(), false);
        commentRepository.save(comment);
        ModelMapper mapper =  new ModelMapper();
        UserResponse userResponse = mapper.map(user, UserResponse.class);
        ArticleResponse articleResponse = mapper.map(article, ArticleResponse.class);
        CommentResponse response = new CommentResponse(comment.getId(),comment.getContent(), userResponse,
                articleResponse);
        return response;
    }

    @Override
    public List<CommentResponse> getComments(Long article_id) {
        articleRepository.findById(article_id).orElseThrow(()-> new BadRequestException(
                ConstantExeptionMessages.MSG_ARTICLE_NOT_FOUND));
        List<Comment> comments = commentRepository.findAllByArticle_id(article_id);
        ModelMapper mapper = new ModelMapper();
        List<CommentResponse> response = Arrays.asList(mapper.map(comments, CommentResponse[].class));
        return response;
    }

    @Override
    public CommentResponse getComment(Long id) throws NotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new NotFoundException(
                ConstantExeptionMessages.MSG_COMMENT_NOT_FOUND));
        ModelMapper mapper = new ModelMapper();
        User user = userRepository.findById(comment.getUser().getId()).orElseThrow(() ->new NotFoundException(
                ConstantExeptionMessages.MSG_USER_NOT_FOUND));
        Article article = articleRepository.findById(comment.getArticle().getId()).orElseThrow(()->
                new NotFoundException(ConstantExeptionMessages.MSG_ARTICLE_NOT_FOUND));
        UserResponse userResponse = mapper.map(user, UserResponse.class);
        ArticleResponse articleResponse = mapper.map(article, ArticleResponse.class);
        CommentResponse response = new CommentResponse(comment.getId(),comment.getContent(),userResponse,articleResponse);
        return response;
    }

    @Override
    public CommentResponse updateComment(Long id, CommentNew commentNew) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new BadRequestException(
                ConstantExeptionMessages.MSG_COMMENT_NOT_FOUND));
        User user = userRepository.findById(commentNew.getUser_id()).orElseThrow(()->
                new BadRequestException(ConstantExeptionMessages.MSG_USER_NOT_FOUND));
        Article article = articleRepository.findById(commentNew.getArticle_id()).orElseThrow(()->
                new BadRequestException(ConstantExeptionMessages.MSG_ARTICLE_NOT_FOUND));
        if (commentNew.getContent() == null || commentNew.getContent() == ""){
            throw new BadRequestException(ConstantExeptionMessages.MSG_COMMENT_EMPTY_CONTENT);
        }else {
            comment.setContent(commentNew.getContent());
        }
        commentRepository.save(comment);
        ModelMapper mapper =new ModelMapper();
        UserResponse userResponse = mapper.map(user, UserResponse.class);
        ArticleResponse articleResponse = mapper.map(article, ArticleResponse.class);
        CommentResponse response = new CommentResponse(comment.getId(),comment.getContent(),
                userResponse, articleResponse);
        return response;
    }

    @Override
    public void deleteComment(Long id) throws NotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new  NotFoundException(
                ConstantExeptionMessages.MSG_COMMENT_NOT_FOUND));
        commentRepository.deleteById(id);
    }
}
