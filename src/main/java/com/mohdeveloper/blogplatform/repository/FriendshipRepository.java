package com.mohdeveloper.blogplatform.repository;

import com.mohdeveloper.blogplatform.entity.Friendship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
    Optional<Friendship> findByRequesterIdAndReceiverId(Long requester, Long receiver);

    @Query(value = "SELECT * FROM blog_platform.friendship WHERE requester_id=?1 OR receiver_id=?1",nativeQuery = true)
    Iterable<Friendship> findByRequesterIdOrReceiverId(Long id);


}
