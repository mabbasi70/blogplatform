package com.mohdeveloper.blogplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Post implements GeneralEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int likesCount;

    @Lob
    @Size(max = 10000, min = 20, message = "at least 20 characters")
    private String caption;

    @OneToOne
    private ImageData imageData;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User author;


    @OneToMany(mappedBy = "post")
    @ToString.Exclude
    private List<Like> likes = new ArrayList<>();


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Tag> taggedUsers = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

}
