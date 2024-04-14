package com.mohdeveloper.blogplatform.service.impl;

import com.mohdeveloper.blogplatform.entity.Comment;
import com.mohdeveloper.blogplatform.entity.Post;
import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.event.CommentEventCreation;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.repository.CommentRepository;
import com.mohdeveloper.blogplatform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostServiceImpl postService;
    private final UserServiceImpl userService;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              PostServiceImpl postService,
                              UserServiceImpl userService,
                              ApplicationEventPublisher applicationEventPublisher){
        this.applicationEventPublisher = applicationEventPublisher;
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Iterable<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Iterable<Comment> findAllByPostId(Long id) {
        return commentRepository.findAllByPostId(id);
    }

    @Override
    @Transactional
    public void addComment(Comment comment, Long postId, SecureUser secureUser) {

        Optional<Post> optionalPost = postService.findById(postId);
        Optional<User> optionalUser = userService.findByEmail(secureUser.getEmail());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            comment.setAuthor(user);
            if(optionalPost.isPresent()){
                Post post = optionalPost.get();
                comment.setPost(post);
                post.getComments().add(comment);
                Comment savedComment = commentRepository.save(comment);
                applicationEventPublisher.publishEvent(new CommentEventCreation(this, savedComment));
            }
        }
    }
}
