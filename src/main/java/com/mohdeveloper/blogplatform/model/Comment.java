package com.mohdeveloper.blogplatform.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;


    @CreationTimestamp
    private LocalDateTime createdAt;


}
