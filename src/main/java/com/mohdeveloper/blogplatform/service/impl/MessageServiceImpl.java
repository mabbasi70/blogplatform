package com.mohdeveloper.blogplatform.service.impl;

import com.mohdeveloper.blogplatform.entity.Message;
import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.repository.MessageRepository;
import com.mohdeveloper.blogplatform.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserServiceImpl userService;

    public MessageServiceImpl(MessageRepository messageRepository,
                              UserServiceImpl userService){
        this.userService = userService;
        this.messageRepository = messageRepository;
    }
    @Override
    public Page<Message> findMessagesBySenderAndReceiver(Pageable pageable, Long senderId, Long receiverId) {
        Optional<User> senderUser = userService.findById(senderId);
        Optional<User> receiverUser = userService.findById(receiverId);
        if(senderUser.isPresent() && receiverUser.isPresent()){
            return messageRepository.findAllBySenderIdAndReceiverId(senderId, receiverId,pageable);
        }else{
            throw new RuntimeException("zart");
        }
    }

    @Override
    @Transactional
    public Message save(Message message, Long senderId, Long receiverId, Principal principal) {

        Optional<User> receiverUser = userService.findById(receiverId);
        Optional<User> senderUser = userService.findById(Long.parseLong(principal.getName()));
        if(!Objects.equals(Long.parseLong(principal.getName()), senderId) && receiverUser.isPresent() && senderUser.isPresent()){
            throw new RuntimeException("something went wrong!!!");
        }else {
            message.setReceiver(receiverUser.get());
            message.setSender(senderUser.get());
            return messageRepository.save(message);
        }
    }



}


