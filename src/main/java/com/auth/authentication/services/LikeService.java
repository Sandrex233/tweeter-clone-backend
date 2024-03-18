package com.auth.authentication.services;

import com.auth.authentication.exceptions.AppException;
import com.auth.authentication.model.ApplicationUser;
import com.auth.authentication.model.Like;
import com.auth.authentication.model.Post;
import com.auth.authentication.repository.LikeRepository;
import com.auth.authentication.repository.PostRepository;
import com.auth.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void likePost(String username, Long postId) {
        ApplicationUser user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException("Post with an Id: " + postId + " not found", HttpStatus.NOT_FOUND));

        boolean alreadyLiked = likeRepository.existsByUserAndPost(user, post);
        if (alreadyLiked) {
            throw new AppException("User has already liked this post", HttpStatus.BAD_REQUEST);
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        likeRepository.save(like);
    }

    @Transactional
    public void unlikePost(String username, Long postId) {
        ApplicationUser user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException("Post with an Id: " + postId + " not found", HttpStatus.NOT_FOUND));

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new AppException("User has not liked this post", HttpStatus.BAD_REQUEST));

        likeRepository.delete(like);
    }
}
