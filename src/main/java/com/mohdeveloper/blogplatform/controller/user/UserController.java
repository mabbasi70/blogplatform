package com.mohdeveloper.blogplatform.controller.user;

import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.service.impl.FriendshipServiceImpl;
import com.mohdeveloper.blogplatform.service.impl.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;
    private final FriendshipServiceImpl friendshipService;
    public UserController (UserServiceImpl userService, FriendshipServiceImpl friendshipService){
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(@RequestParam(value = "search", required = false) String search,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "5") int size,
                           @AuthenticationPrincipal SecureUser secureUser,
                           Model model) {
        Map<Long, String> userFriendIdAndStatusList = friendshipService.userFriendIdAndStatusList(secureUser.getId());
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userBox;
        if(search != null && !search.trim().isEmpty()){
            userBox = userService.findUserByUsername(search, pageable);
        }else{
            userBox = userService.findAll(pageable);
        }
        model.addAttribute("userFriendIdAndStatusList", userFriendIdAndStatusList);
        model.addAttribute("userBox", userBox);
        model.addAttribute("search", search);
        return "users";
    }
    

    @GetMapping("/profile")
    public String getUserProfile(){
        return "profile";
    }



}
