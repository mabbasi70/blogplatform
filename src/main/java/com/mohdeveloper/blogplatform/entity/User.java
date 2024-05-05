package com.mohdeveloper.blogplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;


@Entity
@Data
@RequiredArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Boolean isOnline = false;
    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus = ActivityStatus.OFFLINE;

    @CreationTimestamp
    private Instant lastActivity;

    @NotBlank
    private String email;

//    @NotBlank
    private String password;

    private String username;

    private Boolean enabled = false;

    private String token;

    private LocalDateTime tokeExpiration;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public enum ActivityStatus{
        ONLINE, OFFLINE, AWAY
    }
}
