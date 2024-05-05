package com.mohdeveloper.blogplatform.service.impl;

import com.mohdeveloper.blogplatform.entity.GeneralEntity;
import com.mohdeveloper.blogplatform.entity.Notification;
import com.mohdeveloper.blogplatform.repository.NotificationRepository;
import com.mohdeveloper.blogplatform.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final PostServiceImpl postService;
    private final LikeServiceImpl likeService;
    private final CommentServiceImpl commentService;
    private  final FriendshipServiceImpl friendshipService;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   PostServiceImpl postService,
                                   LikeServiceImpl likeService,
                                   CommentServiceImpl commentService,
                                   FriendshipServiceImpl friendshipService){
        this.friendshipService = friendshipService;
        this.postService = postService;
        this.likeService = likeService;
        this.commentService = commentService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Page<Notification> findByUserId(Pageable pageable, Long receiverId) {
        Page<Notification> notifications = notificationRepository.findByReceiverId(pageable, receiverId);
        notifications.forEach(notification -> {
            GeneralEntity entity = getNotificationEntity(notification.getEntityType().toString(), notification.getEntityId());
            notification.setAssociatedEntity(entity);
        });
        return notifications;
    }

    @Override
    @Transactional
    public void createNotification(Notification notification) {
        notificationRepository.save(notification);
    }



    @Override
    public GeneralEntity getNotificationEntity(String entityType, Long entityId){
        return switch (entityType){
            case "POST" -> postService.findById(entityId).orElse(null);
            case "COMMENT" -> commentService.findById(entityId).orElse(null);
            case "LIKE" -> likeService.findById(entityId).orElse(null);
            case "FRIENDSHIP" -> friendshipService.findById(entityId).orElse(null);
            default -> throw new IllegalArgumentException("Unsupported entity type");
        };
    }
}
