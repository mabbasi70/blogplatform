package com.mohdeveloper.blogplatform.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class home {

    @GetMapping("/")
    public String home(){
        return "home";
    }
    
    @GetMapping("/signup")
    public String getSignup() {
        return "signup";
    }
    
}
