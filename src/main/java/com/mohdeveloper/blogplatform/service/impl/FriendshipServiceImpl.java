package com.mohdeveloper.blogplatform.service.impl;

import com.mohdeveloper.blogplatform.entity.Friendship;
import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.event.FriendshipEventAcceptance;
import com.mohdeveloper.blogplatform.event.FriendshipEventCreation;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.repository.FriendshipRepository;
import com.mohdeveloper.blogplatform.service.FriendshipService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserServiceImpl userService;
    public FriendshipServiceImpl(FriendshipRepository friendshipRepository,
                                 UserServiceImpl userService,
                                 ApplicationEventPublisher applicationEventPublisher){
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.friendshipRepository = friendshipRepository;

    }
    @Override
    @Transactional
    public Friendship save(Friendship friendship) {
        return friendshipRepository.save(friendship);
    }

    @Override
    public Optional<Friendship> findByRequesterIdAndReceiverId(Long requester, Long receiver) {
        return friendshipRepository.findByRequesterIdAndReceiverId(requester, receiver);
    }

    @Override
    @Transactional
    public Friendship sendFriendRequest(Long requesterId, User receiver) {
            Friendship newFriendship = new Friendship();
            Optional<User> requesterUser = userService.findById(requesterId);
            newFriendship.setRequester(requesterUser.get());
            newFriendship.setReceiver(receiver);
            Friendship savedFriendship = save(newFriendship);
            applicationEventPublisher.publishEvent(new FriendshipEventCreation(this, savedFriendship));
            return savedFriendship;
    }

    @Override
    @Transactional
    public Friendship acceptFriendRequest(Long userId, String answer, Long friendshipId) {
        SecureUser secureUser = (SecureUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Friendship> friendshipOptional = friendshipRepository.findById(friendshipId);
        if (Objects.equals(secureUser.getId(), userId) && friendshipOptional.isPresent()) {
            Friendship friendship = friendshipOptional.get();
            Friendship updatedFriendship;
            if(Objects.equals(answer, "accept")) {
                friendship.setStatus(Friendship.Status.FRIEND);
                friendship.setRespondAt(LocalDateTime.now());
                updatedFriendship = friendshipRepository.save(friendship);
                applicationEventPublisher.publishEvent(new FriendshipEventAcceptance(this, updatedFriendship));
            }else {
                friendship.setStatus(Friendship.Status.REJECTED);
                friendship.setRespondAt(LocalDateTime.now());
                updatedFriendship = friendshipRepository.save(friendship);

            }
                      return updatedFriendship;
        } else {
            throw new EntityNotFoundException("Friendship not found");
        }
    }

    @Transactional
    public Friendship friendshipRejection(Long frienshipId){
        return null;
    }

    @Override
    public Map<Long, String> userFriendIdAndStatusList(Long id) {
        Iterable<Friendship> friendships = friendshipRepository.findByRequesterIdOrReceiverId(id);
        Map<Long, String> friendshipsData = new HashMap<>();
       for(Friendship friendship: friendships){
           Long friendId = id.equals(friendship.getRequester().getId()) ? friendship.getReceiver().getId() : friendship.getRequester().getId();
           friendshipsData.put(friendId, friendship.getStatus().toString());

       }
        return friendshipsData;

    }

    @Override
    public Optional<Friendship> findById(Long id) {
        return friendshipRepository.findById(id);
    }

}
