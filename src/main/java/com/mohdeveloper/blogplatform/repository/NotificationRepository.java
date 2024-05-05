package com.mohdeveloper.blogplatform.repository;

import com.mohdeveloper.blogplatform.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    Page<Notification> findByReceiverId(Pageable pageable, Long receiverId);
}
