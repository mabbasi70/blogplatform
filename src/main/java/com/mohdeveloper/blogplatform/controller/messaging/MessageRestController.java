package com.mohdeveloper.blogplatform.controller.messaging;

import com.mohdeveloper.blogplatform.entity.Message;
import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.service.impl.MessageServiceImpl;
import com.mohdeveloper.blogplatform.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageRestController {

    private final MessageServiceImpl messageService;
    private final UserServiceImpl userService;

    public MessageRestController(MessageServiceImpl messageService,
                                 UserServiceImpl userService){
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/api/messages/{userId}")
    public ResponseEntity<?> getUsersForScroll(@PathVariable Long userId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "15") int size,
                                               @AuthenticationPrincipal SecureUser secureUser){
        try{
            Pageable pageable = PageRequest.of(page,size);
            Page<User> chatBoxUsers = userService.findUserChats(secureUser,userId,pageable);
            return ResponseEntity.ok(chatBoxUsers.getContent());
        }catch (Exception e){

            return ResponseEntity.internalServerError().body("Error loading users!");
        }

    }

    @GetMapping("/api/messages/{userId}/{receiverId}")
    public ResponseEntity<?> getMessagesForScroll(@PathVariable Long userId,
                                                  @PathVariable Long receiverId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "12") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Message> messageBox = messageService.findMessagesBySenderAndReceiver(pageable, userId, receiverId);

            return ResponseEntity.ok(messageBox.getContent());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error loading messages");
        }
    }


//    @PostMapping("/api/messages/{senderId}/{receiverId}")
//    public ResponseEntity<?> postMessage(@Valid @RequestBody Message message,
//                                         @PathVariable Long senderId,
//                                         @PathVariable Long receiverId,
//                                         @AuthenticationPrincipal SecureUser secureUser) {
//        try {
//            messageService.save(message, senderId, receiverId, secureUser);
//            return ResponseEntity.ok("Message sent successfully to " + receiverId);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body("Failed to send message: " + e.getMessage());
//        }
//    }
}
