package com.mohdeveloper.blogplatform.service;

import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.model.SignupUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    User createUser(SignupUser signupUser);

    Page<User> findUserByUsername(String username, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);

    Optional<User> findByToken(String token);

    void verifyEmailAndSetTokenToNull(String token);

    Page<User> findUserChats(SecureUser secureUser, Long currentUserId, Pageable pageable);
    User save(User user);
    List<User> findUsersByActivityStatusOnline();

    Boolean send6CharToken(String email);

    Boolean checkToken(String email, String token);

}
