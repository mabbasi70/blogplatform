package com.mohdeveloper.blogplatform.repository;

import com.mohdeveloper.blogplatform.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Optional<User> findByToken(String token);
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT u.* " +
            "FROM (" +
            "    SELECT *, " +
            "           ROW_NUMBER() OVER (PARTITION BY LEAST(sender_id, receiver_id), GREATEST(sender_id, receiver_id) ORDER BY time_stamp DESC) as rn, " +
            "           CASE " +
            "               WHEN sender_id = ?1 THEN receiver_id " +
            "               WHEN receiver_id = ?1 THEN sender_id " +
            "           END AS other_user_id " +
            "    FROM Message " +
            "    WHERE sender_id = ?1 OR receiver_id = ?1" +
            ") m " +
            "JOIN User u ON u.id = m.other_user_id " +
            "WHERE m.rn = 1 order by time_stamp DESC",
            countQuery = "SELECT COUNT(DISTINCT CASE " +
                    "WHEN sender_id = ?1 THEN receiver_id " +
                    "WHEN receiver_id = ?1 THEN sender_id " +
                    "END) " +
                    "FROM Message " +
                    "WHERE sender_id = ?1 OR receiver_id = ?1",
            nativeQuery = true)
    Page<User> findUserChats(Long currentUserId, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.activityStatus = 'ONLINE'")
    List<User> findUsersByActivityStatusOnline();

    @Modifying
    @Query("UPDATE User u SET u.lastActivity = ?2 WHERE u.username = ?1")
    void updateLastActivity(String username, Instant lastActivity);

}
