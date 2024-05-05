package com.mohdeveloper.blogplatform.service.impl;

import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.entity.UserTokenExpiry;
import com.mohdeveloper.blogplatform.repository.UserTokenExpiryRepository;
import com.mohdeveloper.blogplatform.service.UserTokenExpiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserTokenExpiryImpl implements UserTokenExpiryService {
    private final UserTokenExpiryRepository userTokenExpiryRepository;
    @Override
    public UserTokenExpiry save(UserTokenExpiry userTokenExpiry) {
        return userTokenExpiryRepository.save(userTokenExpiry);
    }

    @Override
    public Optional<UserTokenExpiry> findByUser(User user) {
        return userTokenExpiryRepository.findByUser(user);
    }
}
