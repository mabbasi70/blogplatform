package com.mohdeveloper.blogplatform.event;

import com.mohdeveloper.blogplatform.entity.Comment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentEventCreation extends ApplicationEvent {
    private final Comment comment;

    public CommentEventCreation(Object source, Comment comment) {
        super(source);
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }
}
