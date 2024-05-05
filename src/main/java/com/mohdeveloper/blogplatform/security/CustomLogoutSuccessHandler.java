package com.mohdeveloper.blogplatform.security;

import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final UserServiceImpl userService;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if((authentication != null) && (authentication.getName() != null)){
            String username = authentication.getName();
            userService.findByUsername(username).ifPresent(user -> {
                user.setActivityStatus(User.ActivityStatus.OFFLINE);
                userService.save(user);
            });
        }
        request.getSession().invalidate();
        response.sendRedirect("/logout-success");
    }
}
