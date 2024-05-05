package com.mohdeveloper.blogplatform.service.impl;

import com.mohdeveloper.blogplatform.entity.Like;
import com.mohdeveloper.blogplatform.entity.Notification;
import com.mohdeveloper.blogplatform.entity.Post;
import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.event.LikeEventCreation;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.repository.LikeRepository;
import com.mohdeveloper.blogplatform.service.LikeService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostServiceImpl postService;
    private final UserServiceImpl userService;
    private final NotificationServiceImpl notificationService;

    private ApplicationEventPublisher applicationEventPublisher;

    public LikeServiceImpl(LikeRepository likeRepository,
                           PostServiceImpl postService,
                           UserServiceImpl userService,
                           ApplicationEventPublisher applicationEventPublisher,
                           @Lazy NotificationServiceImpl notificationService){
        this.applicationEventPublisher = applicationEventPublisher;
        this.notificationService = notificationService;
        this.userService = userService;
        this.likeRepository = likeRepository;
        this.postService = postService;
    }

    @Override
    public Optional<Like> findById(Long id) {
        return likeRepository.findById(id);
    }

    @Override
    @Transactional
    public void addLikeOrRemoveLike(Long postId, SecureUser secureUser) {
        Optional<Post> optionalPost = postService.findById(postId);
        Optional<User> optionalUser = userService.findByEmail(secureUser.getEmail());

        if(optionalUser.isPresent() && optionalPost.isPresent()){
            User user = optionalUser.get();
            Post post = optionalPost.get();
            if(!likeRepository.existsByPostAndLikedBy(post, user)){
                Like like = new Like();
                like.setLikedBy(user);
                like.setPost(post);
                post.getLikes().add(like);
                post.setLikesCount(post.getLikesCount()+1);
                Like savedLike = likeRepository.save(like);
                applicationEventPublisher.publishEvent(new LikeEventCreation(this, savedLike));

            }else{
                post.setLikesCount(post.getLikesCount()-1);
                likeRepository.deleteByPostIdAndUserId(postId,secureUser.getId());
            }
        }
    }

}
