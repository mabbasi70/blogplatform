package com.mohdeveloper.blogplatform.event;

import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.service.impl.UserServiceImpl;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@WebListener
@RequiredArgsConstructor
public class SessionListener implements HttpSessionListener {

    private final UserServiceImpl userService;
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSessionListener.super.sessionCreated(se);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
       String username = (String) se.getSession().getAttribute("username");
       if(username != null){
          userService.findByUsername(username).ifPresent(user -> {
              user.setActivityStatus(User.ActivityStatus.OFFLINE);
              userService.save(user);
          });
       }
    }
}
