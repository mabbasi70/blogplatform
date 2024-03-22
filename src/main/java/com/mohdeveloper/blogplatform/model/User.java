package com.mohdeveloper.blogplatform.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String username;

    private Integer enabled;


    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends= new ArrayList<>();


    @OneToMany(mappedBy = "likedBy",fetch = FetchType.EAGER)
    private List<Like> likes = new ArrayList<>();


    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

}
