package com.mohdeveloper.blogplatform.event;

import com.mohdeveloper.blogplatform.entity.Like;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class LikeEventCreation extends ApplicationEvent {
    private final Like like;

    public LikeEventCreation(Object source, Like like) {
        super(source);
        this.like = like;
    }

}
