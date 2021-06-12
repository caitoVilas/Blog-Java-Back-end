package com.caito.blogjava.repository;

import com.caito.blogjava.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findAllByArticle_id(Long article_id);
}
