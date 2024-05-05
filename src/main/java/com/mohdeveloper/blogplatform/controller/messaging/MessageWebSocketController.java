package com.mohdeveloper.blogplatform.controller.messaging;

import com.mohdeveloper.blogplatform.entity.Message;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.service.impl.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class MessageWebSocketController {

    private final MessageServiceImpl messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.private/{receiverId}")
    public void processPrivateMessage(@Payload Message message, Principal principal,@DestinationVariable String receiverId) {
        try {
            Message savedMessage = messageService.save(message, message.getSender().getId(), message.getReceiver().getId(), principal);
            messagingTemplate.convertAndSendToUser(principal.getName(),"/queue/reply", savedMessage);
            messagingTemplate.convertAndSendToUser(receiverId,"/queue/reply",savedMessage);

        } catch (Exception e) {
            throw new MessagingException("Could not process message", e);
        }
    }
}
