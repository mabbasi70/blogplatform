package com.mohdeveloper.blogplatform.service.event;

import com.mohdeveloper.blogplatform.entity.Friendship;
import com.mohdeveloper.blogplatform.entity.Notification;
import com.mohdeveloper.blogplatform.event.FriendshipEventAcceptance;
import com.mohdeveloper.blogplatform.event.FriendshipEventCreation;

import com.mohdeveloper.blogplatform.service.impl.NotificationServiceImpl;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FriendshipEventHandler {

    private final NotificationServiceImpl notificationService;

    public FriendshipEventHandler(NotificationServiceImpl friendshipService){
        this.notificationService = friendshipService;
    }

    @EventListener
    public void handleFriendshipCreation(FriendshipEventCreation event){
        notifyReceiverRequest(event.getFriendship());
    }

    private void notifyReceiverRequest(Friendship friendship){
        String message =
                friendship.getRequester().getUsername() +
                ": has sent a friend request";
        Notification notification = Notification.builder()
                .entityId(friendship.getId())
                .entityType(Notification.EntityType.FRIENDSHIP)
                .creator(friendship.getRequester())
                .receiver(friendship.getReceiver())
                .message(message)
                .build();
        notificationService.createNotification(notification);


    }

    @EventListener
    public void handleFriendshipAcceptance(FriendshipEventAcceptance event) {
        notifyRequesterOfAcceptance(event.getFriendship());
    }

    private void notifyRequesterOfAcceptance(Friendship friendship) {

            String message = "Your friend request to " +
                    friendship.getReceiver().getUsername() +
                    " has been accepted.";
            Notification notification = Notification.builder()
                    .entityId(friendship.getId())
                    .entityType(Notification.EntityType.FRIENDSHIP)
                    .creator(friendship.getReceiver())
                    .receiver(friendship.getRequester())
                    .message(message)
                    .build();
            notificationService.createNotification(notification);

    }
}
