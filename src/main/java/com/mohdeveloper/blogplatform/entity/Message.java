package com.mohdeveloper.blogplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @NotBlank(message = "can not be empty")
    private String text;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean senderDeleted = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean receiverDeleted = false;

    @CreationTimestamp
    private LocalDateTime timeStamp;



}
