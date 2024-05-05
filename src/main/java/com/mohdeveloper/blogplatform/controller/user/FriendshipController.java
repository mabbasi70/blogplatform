package com.mohdeveloper.blogplatform.controller.user;

import com.mohdeveloper.blogplatform.entity.Friendship;
import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.repository.NotificationRepository;
import com.mohdeveloper.blogplatform.service.NotificationService;
import com.mohdeveloper.blogplatform.service.impl.FriendshipServiceImpl;
import com.mohdeveloper.blogplatform.service.impl.NotificationServiceImpl;
import com.mohdeveloper.blogplatform.service.impl.UserServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Controller
public class FriendshipController {

    private final FriendshipServiceImpl friendshipService;
    private final UserServiceImpl userService;
    private final NotificationRepository notificationRepository;

    public FriendshipController(FriendshipServiceImpl friendshipService,
                                UserServiceImpl userService,
                                NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

    @PostMapping("/friendship-requests")
    public String sendFriendRequest(@RequestParam("requesterId") Long requesterId,
                                    @RequestParam("receiverId") Long receiverId,
                                    @AuthenticationPrincipal SecureUser secureUser){

        Optional<Friendship> optionalFriendship = friendshipService.findByRequesterIdAndReceiverId(requesterId, receiverId);
        Optional<User> optionalReceiver = userService.findById(receiverId);

        if(optionalFriendship.isEmpty() && optionalReceiver.isPresent() && Objects.equals(secureUser.getId(), requesterId)){
            friendshipService.sendFriendRequest(requesterId, optionalReceiver.get());
        }

        return "redirect:/users";
    }

    @PostMapping("/friendship-requests/answer")
    public String friendshipRequestAnswer(@RequestParam(value = "userId") Long userId,
                                          @RequestParam(value="answer") String answer,
                                          @RequestParam(value = "entityId") Long entityId,
                                          @RequestParam(value = "notifId") Long nofifId){

        try{
            notificationRepository.findById(nofifId).ifPresentOrElse(n -> {
                n.setUpdatedAt(LocalDateTime.now());
                notificationRepository.save(n);
                },
                    ()->{
                throw new RuntimeException("Could not update notification!");
            });
            friendshipService.acceptFriendRequest(userId, answer, entityId);
            return "redirect:/notifications?userId="+userId;

        }catch(Exception e){
            return null;
        }
    }
}
