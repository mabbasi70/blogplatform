package com.mohdeveloper.blogplatform.service.event;

import com.mohdeveloper.blogplatform.entity.Comment;
import com.mohdeveloper.blogplatform.entity.Notification;
import com.mohdeveloper.blogplatform.event.CommentEventCreation;
import com.mohdeveloper.blogplatform.service.impl.NotificationServiceImpl;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CommentEventHandler {

    private final NotificationServiceImpl notificationService;

    public CommentEventHandler(NotificationServiceImpl notificationService){
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleCommentCreation(CommentEventCreation event){
        notifyPostOwner(event.getComment());
    }

    private void notifyPostOwner(Comment comment){
        String message = comment.getAuthor().getEmail() + " add a comment to your post: " + comment.getPost().getCaption();
        Notification notification = Notification.builder()
                .entityId(comment.getId())
                .entityType(Notification.EntityType.COMMENT)
                .creator(comment.getAuthor())
                .receiver(comment.getPost().getAuthor())
                .message(message)
                .build();
        notificationService.createNotification(notification);
    }

}
