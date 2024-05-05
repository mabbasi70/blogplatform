package com.mohdeveloper.blogplatform.service.impl;

import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class OAuth2UserServiceImpl implements  OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    public OAuth2UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");
        return userRepository
                .findByEmail(email)
                .map(user -> new SecureUser(user, attributes))
                .orElseGet(() -> new SecureUser(createNewUserFromOAuth2Attributes(attributes), attributes));
    }

    private User createNewUserFromOAuth2Attributes(Map<String, Object> attributes) {
        User user = new User();
        String username = ((String) attributes.get("email")).split("@")[0];
        user.setEmail((String) attributes.get("email"));
        user.setUsername(username);
        user.setEnabled(true);
        user.setPassword(null);
        return userRepository.save(user);
    }
}
