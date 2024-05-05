package com.mohdeveloper.blogplatform.service;

import com.mohdeveloper.blogplatform.entity.Message;
import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface MessageService {

    Page<Message> findMessagesBySenderAndReceiver(Pageable pageable, Long senderId, Long receiverId);

    Message save (Message message, Long senderId, Long receiverId, Principal principal);

}
