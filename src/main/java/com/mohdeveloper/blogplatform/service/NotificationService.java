package com.mohdeveloper.blogplatform.service;

import com.mohdeveloper.blogplatform.entity.GeneralEntity;
import com.mohdeveloper.blogplatform.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {

    Page<Notification> findByUserId(Pageable pageable, Long userId);
    void createNotification(Notification notification);

    public GeneralEntity getNotificationEntity(String entityType, Long entityId);
}
