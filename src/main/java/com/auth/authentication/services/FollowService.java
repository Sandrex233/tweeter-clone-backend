package com.auth.authentication.services;


import com.auth.authentication.exceptions.AppException;
import com.auth.authentication.exceptions.DuplicateFollowException;
import com.auth.authentication.exceptions.FollowNotFoundException;
import com.auth.authentication.model.ApplicationUser;
import com.auth.authentication.model.Follow;
import com.auth.authentication.model.UserInfo;
import com.auth.authentication.repository.FollowRepository;
import com.auth.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void followUser(String username, Long toFollowId) {
        ApplicationUser follower = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        ApplicationUser followed = userRepository.findById(Math.toIntExact(toFollowId)).orElseThrow(() -> new AppException("User not found with ID: " + toFollowId, HttpStatus.NOT_FOUND));

        Long userId = Long.valueOf(follower.getUserId());

        if(userId.equals(toFollowId)){
            throw new IllegalArgumentException("Users cannot follow themselves.");
        }

        try {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowed(followed);
            followRepository.save(follow);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateFollowException("You are already following this user");
        }
    }

    @Transactional
    public void unfollowUser(String username, Long followedId) {
        ApplicationUser follower = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (!followRepository.existsByFollowerIdAndFollowedId(Long.valueOf(follower.getUserId()), followedId)) {
            throw new FollowNotFoundException("You are not following this user");
        }

        followRepository.deleteByFollowerIdAndFollowedId(Long.valueOf(follower.getUserId()), followedId);
    }

    public Page<UserInfo> getUserFollowers(Long userId, int page, int size) {
        return followRepository.findFollowersByFollowedId(userId, PageRequest.of(page, size));
    }

    public Page<UserInfo> getUserFollowings(Long userId, int page, int size) {
        return followRepository.findFollowingByFollowerId(userId, PageRequest.of(page, size));
    }
}
