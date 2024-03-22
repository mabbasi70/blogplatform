package com.mohdeveloper.blogplatform.repository;

import com.mohdeveloper.blogplatform.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
