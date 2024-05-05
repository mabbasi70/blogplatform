package com.mohdeveloper.blogplatform.security;

import com.mohdeveloper.blogplatform.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UserActivityInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getUserPrincipal() != null) {
            String username = request.getUserPrincipal().getName();
            updateLastActivity(username);
        }
        return true;
    }

    private void updateLastActivity(String username) {
        userRepository.updateLastActivity(username, Instant.now());
    }
}
