package com.mohdeveloper.blogplatform.service;

import com.mohdeveloper.blogplatform.entity.Comment;
import com.mohdeveloper.blogplatform.model.SecureUser;

public interface CommentService {

    Comment save(Comment comment);
    Iterable<Comment> findAll();

    Iterable<Comment> findAllByPostId(Long id);

    void addComment(Comment comment, Long postId, SecureUser secureUser);

}
