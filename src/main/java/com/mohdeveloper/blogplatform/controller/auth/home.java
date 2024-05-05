package com.mohdeveloper.blogplatform.controller.auth;

import com.mohdeveloper.blogplatform.model.SecureUser;
import org.aspectj.bridge.IMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class home {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        boolean loggingError = error != null;
        if (loggingError) {
            model.addAttribute("messageType", "error");
            model.addAttribute("message", "Invalid Username or Password.");
        }
        return "login";
    }

    @GetMapping("/post-login")
    public String postLogin(RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal SecureUser secureUser){
        redirectAttributes.addFlashAttribute("messageType","success");
        redirectAttributes.addFlashAttribute("message", "Welcome Back " + secureUser.getUsername());
        return "redirect:/posts";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("messageType", "success");
        redirectAttributes.addFlashAttribute("message", "You have been logged out successfully.");
        return "redirect:/login";
    }

    @GetMapping("/password-reset")
    public String passwordReset(){
        return "password-reset";
    }


}
