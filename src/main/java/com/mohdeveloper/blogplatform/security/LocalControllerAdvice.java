package com.mohdeveloper.blogplatform.security;

import com.mohdeveloper.blogplatform.model.SecureUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class LocalControllerAdvice {

    @ModelAttribute("currentUser")
    public SecureUser currentUser(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof SecureUser) {
            return (SecureUser) authentication.getPrincipal();
        }
        return null;
    }
}
