package com.mohdeveloper.blogplatform.service;

import com.mohdeveloper.blogplatform.entity.Like;
import com.mohdeveloper.blogplatform.model.SecureUser;

import java.util.Optional;

public interface LikeService {

    Optional<Like> findById(Long id);

    void addLikeOrRemoveLike(Long postId, SecureUser secureUser);

}
