package com.mohdeveloper.blogplatform.repository;

import com.mohdeveloper.blogplatform.entity.Message;
import com.mohdeveloper.blogplatform.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    //    @Query(value = "SELECT * FROM MESSAGE WHERE (sender_id=?1 and receiver_id=?2) or (sender_id=?2 and receiver_id=?1) ORDER BY time_stamp DESC", nativeQuery = true)
    @Query(value = "SELECT * FROM MESSAGE WHERE (sender_id=?1 AND receiver_id=?2) OR (sender_id=?2 AND receiver_id=?1) ORDER BY time_stamp DESC",
            countQuery = "SELECT COUNT(*) FROM MESSAGE WHERE (sender_id=?1 AND receiver_id=?2) OR (sender_id=?2 AND receiver_id=?1)",
            nativeQuery = true)
    Page<Message> findAllBySenderIdAndReceiverId(Long senderId, Long receiverId, Pageable pageable);

    @Query(value = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (PARTITION BY LEAST(sender_id, receiver_id), GREATEST(sender_id, receiver_id)" +
            " ORDER BY time_stamp DESC) as rn FROM Message WHERE sender_id =?1 OR receiver_id=?1) as m WHERE m.rn = 1", nativeQuery = true)
    List<Message> findLatestMessagesForAllUserPairs(Long userId);


}
