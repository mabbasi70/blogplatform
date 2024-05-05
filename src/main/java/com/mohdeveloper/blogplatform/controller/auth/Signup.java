package com.mohdeveloper.blogplatform.controller.auth;

import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.model.SignupUser;
import com.mohdeveloper.blogplatform.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/signup")
public class Signup {

    private final UserServiceImpl userService;

    public Signup(UserServiceImpl userService){
        this.userService = userService;
    }


    @GetMapping
    public String getSignup(Model model){
        model.addAttribute("user", new SignupUser());
        return "signup";
    }

    @PostMapping
    public String postSignup(@ModelAttribute("user") SignupUser signupUser,
                             RedirectAttributes redirectAttributes) {
        try{
            userService.createUser(signupUser);
            redirectAttributes.addFlashAttribute("messageType", "success");
            redirectAttributes.addFlashAttribute("message", "Your Account has been Confirmed!");
            return "redirect:/login";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("messageType", "error");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/verify-email")
    public String emailConfirmation(@RequestParam String token,
                                    RedirectAttributes redirectAttributes){
        try{
            userService.verifyEmailAndSetTokenToNull(token);
            redirectAttributes.addFlashAttribute("messageType", "success");
            redirectAttributes.addFlashAttribute("message", "Your Account has been Confirmed!");
            return "redirect:/login";


        }catch (Exception e){
            redirectAttributes.addFlashAttribute("messageType", "error");
            redirectAttributes.addFlashAttribute("message", "Some thing went wrong!");
            return "redirect:/login";

        }

    }
}
