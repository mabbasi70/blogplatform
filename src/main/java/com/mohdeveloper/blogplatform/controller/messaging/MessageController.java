package com.mohdeveloper.blogplatform.controller.messaging;

import com.mohdeveloper.blogplatform.entity.Message;
import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.service.impl.MessageServiceImpl;
import com.mohdeveloper.blogplatform.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final UserServiceImpl userService;
    private final MessageServiceImpl messageService;
    public MessageController(UserServiceImpl userService,
                             MessageServiceImpl messageService){
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public String getMessages(@PathVariable Long userId,
                              Model model,
                              @AuthenticationPrincipal SecureUser secureUser){
        try{
//            Page<User> chatBoxUsers;
//            Pageable pageable = PageRequest.of(0,15);
//            chatBoxUsers = messageService.findMessageBoxes(secureUser,userId,pageable);
//            model.addAttribute("chatBoxes", chatBoxUsers);
            return "messages";
        }catch (Exception e){
            return null;
        }

    }

    @GetMapping("/{userId}/{receiverId}")
    public String getMessages(@PathVariable Long userId, @PathVariable Long receiverId,
                              Model model){
        try{
//            Page<Message> messageBox;
//            Pageable pageable = PageRequest.of(0,10);
//            messageBox = messageService.findMessagesBySenderAndReceiver(pageable,userId,receiverId);
//            List<Message> messages = new ArrayList<>(messageBox.getContent());
//            Collections.reverse(messages);
            User receiverUser = userService.findById(receiverId).get();
//            model.addAttribute("message",new Message());
            model.addAttribute("receiverUser", receiverUser);
//            model.addAttribute("messageBox", messageBox);
//            model.addAttribute("messages", messages);
            return "messages";
        }catch (Exception e){
            return "messages";
        }
    }




//    @PostMapping
//    public String postMessage(@Valid @ModelAttribute("message") Message message,
//                              BindingResult bindingResult,
//                              @RequestParam Long senderId,
//                              @RequestParam Long receiverId,
//                              @AuthenticationPrincipal SecureUser secureUser){
//
//        try{
//            messageService.save(message, senderId, receiverId, secureUser);
//            return "redirect:/messages/" + senderId +"/" + receiverId;
//        }catch (Exception e){
//            return null;
//        }
//    }

}
