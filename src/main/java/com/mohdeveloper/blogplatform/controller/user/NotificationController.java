package com.mohdeveloper.blogplatform.controller.user;

import com.mohdeveloper.blogplatform.entity.Notification;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.service.impl.NotificationServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationServiceImpl notificationService;
    public  NotificationController(NotificationServiceImpl notificationService){
        this.notificationService = notificationService;
    }
    @GetMapping
    public String getNotifications(@AuthenticationPrincipal SecureUser secureUser,
                                   @RequestParam(value = "userId") Long userId,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                   Model model
                                   ){
        Page<Notification> notificationBox;
        Pageable pageable = PageRequest.of(page,size);
        if(!(Objects.equals(userId, secureUser.getId()))){
            return null;
        }
        notificationBox = notificationService.findByUserId(pageable, userId);
        model.addAttribute("notificationBox", notificationBox);
        return "notifications";
    }
}
