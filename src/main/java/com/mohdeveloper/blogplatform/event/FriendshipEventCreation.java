package com.mohdeveloper.blogplatform.event;

import com.mohdeveloper.blogplatform.entity.Friendship;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FriendshipEventCreation extends ApplicationEvent {
    private final Friendship friendship;
    public FriendshipEventCreation(Object source, Friendship friendship) {
        super(source);
        this.friendship = friendship;
    }
}
