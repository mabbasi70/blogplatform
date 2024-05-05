package com.mohdeveloper.blogplatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Friendship implements GeneralEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING ;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime respondAt;
    public enum Status{
        PENDING, FRIEND, REJECTED, CANCELLED, YOURSELF
    }
}


