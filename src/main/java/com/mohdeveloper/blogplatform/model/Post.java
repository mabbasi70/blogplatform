package com.mohdeveloper.blogplatform.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private BaseUser author;


    @OneToMany(mappedBy = "post")
    private List<Like> likes = new ArrayList<>();


    @OneToMany()
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Tag> taggedUsers = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

}
