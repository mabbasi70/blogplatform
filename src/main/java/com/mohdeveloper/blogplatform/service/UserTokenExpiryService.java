package com.mohdeveloper.blogplatform.service;

import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.entity.UserTokenExpiry;

import java.util.Optional;

public interface UserTokenExpiryService {
    UserTokenExpiry save(UserTokenExpiry userTokenExpiry);

    Optional<UserTokenExpiry> findByUser(User user);
}

