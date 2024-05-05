package com.mohdeveloper.blogplatform.repository;

import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.entity.UserTokenExpiry;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserTokenExpiryRepository extends CrudRepository<UserTokenExpiry, Long> {
    Optional<UserTokenExpiry> findByUser(User user);
}

