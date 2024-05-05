package com.mohdeveloper.blogplatform.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements GeneralEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "`read`", columnDefinition = "BIT")
    private boolean read;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private EntityType entityType;

    private Long entityId;

    private LocalDateTime updatedAt;

    public enum EntityType{
        POST, LIKE, COMMENT, FRIENDSHIP
    }

    @Transient
    private GeneralEntity associatedEntity;

}

