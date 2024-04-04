package com.mohdeveloper.blogplatform.repository;

import com.mohdeveloper.blogplatform.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {


    @Query(value = "SELECT * FROM blog_platform.comment WHERE post_id=?1", nativeQuery = true)
    Iterable<Comment> findAllByPostId(Long id);

}
