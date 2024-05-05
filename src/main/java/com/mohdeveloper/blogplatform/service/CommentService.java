package com.mohdeveloper.blogplatform.service;

import com.mohdeveloper.blogplatform.entity.Comment;
import com.mohdeveloper.blogplatform.model.SecureUser;

import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(Long id);
    Comment save(Comment comment);
    Iterable<Comment> findAll();

    Iterable<Comment> findAllByPostId(Long id);

    void addComment(Comment comment, Long postId, SecureUser secureUser);

}
