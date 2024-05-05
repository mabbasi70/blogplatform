package com.mohdeveloper.blogplatform.repository;

import com.mohdeveloper.blogplatform.entity.Like;
import com.mohdeveloper.blogplatform.entity.Post;
import com.mohdeveloper.blogplatform.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LikeRepository extends CrudRepository<Like, Long> {


    @Query(value = "SELECT EXISTS(SELECT 1 FROM blog_platform.likes WHERE post_id=?1 AND user_id=?2)",
            nativeQuery = true)
    boolean existByUserIdAndPostId(Long postId, Long UserId);



    @Query(value = "DELETE FROM blog_platform.likes WHERE post_id = :postId AND user_id = :userId", nativeQuery = true)
    @Modifying
    int deleteByPostIdAndUserId(Long postId, Long userId);

    boolean existsByPostAndLikedBy(Post post, User user);

}
