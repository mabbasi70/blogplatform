package com.mohdeveloper.blogplatform.model;

import java.util.Date;
import java.util.List;

public class Post {

    private Integer id;
    private String description;
    private User author;
    private List<User> likes;
    private List<Comment> comments;

    private List<User> taggedUsers;
    private Date createdAt;

}
