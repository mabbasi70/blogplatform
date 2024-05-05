package com.mohdeveloper.blogplatform.service.event;

import com.mohdeveloper.blogplatform.entity.Like;
import com.mohdeveloper.blogplatform.entity.Notification;
import com.mohdeveloper.blogplatform.event.LikeEventCreation;
import com.mohdeveloper.blogplatform.service.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LikeEventHandler {

    private final NotificationService notificationService;

    public LikeEventHandler(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleLikeCreation(LikeEventCreation event) {
        notifyPostOwner(event.getLike());
    }

    private void notifyPostOwner(Like like) {
        String first15 = like.getPost().getCaption().substring(0,15);
        String message = "Your post titled '" + first15 + "' was liked by " + like.getLikedBy().getEmail();
        Notification notification = Notification.builder()
                .entityId(like.getId())
                .entityType(Notification.EntityType.LIKE)
                .message(message)
                .creator(like.getLikedBy())
                .receiver(like.getPost().getAuthor())
                .build();

        notificationService.createNotification(notification);
    }

}
