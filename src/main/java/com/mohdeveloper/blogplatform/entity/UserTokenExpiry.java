package com.mohdeveloper.blogplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class UserTokenExpiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    User user;

    String token;

    LocalDateTime tokenExpiry;

    LocalDateTime updatedAt = LocalDateTime.now();
}
