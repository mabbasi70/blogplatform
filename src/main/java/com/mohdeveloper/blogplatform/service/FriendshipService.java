package com.mohdeveloper.blogplatform.service;

import com.mohdeveloper.blogplatform.entity.Friendship;
import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FriendshipService {



    Friendship save(Friendship friendship);

    Optional<Friendship> findByRequesterIdAndReceiverId(Long requester, Long receiver);

    Friendship sendFriendRequest(Long requesterId, User receiver);

    Friendship acceptFriendRequest(Long userId, String answer, Long friendshipId);

    Map<Long, String> userFriendIdAndStatusList(Long id);

    Optional<Friendship> findById(Long id);

}
