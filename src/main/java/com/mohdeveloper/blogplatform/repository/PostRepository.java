package com.mohdeveloper.blogplatform.repository;

import com.mohdeveloper.blogplatform.entity.Post;
import com.mohdeveloper.blogplatform.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    Page<Post> findAllByAuthor(User author, Pageable pageable);

    @Query(value = "SELECT * FROM blog_platform.post WHERE user_id=?1", nativeQuery = true)
    Iterable<Post> findAllByUserId(Long id);
}
