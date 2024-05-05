package com.mohdeveloper.blogplatform.event;

import com.mohdeveloper.blogplatform.entity.Post;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostEventCreation extends ApplicationEvent {
    private final Post post;

    public PostEventCreation(Object source, Post post) {
        super(source);
        this.post = post;
    }
}
