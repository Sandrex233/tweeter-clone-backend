package com.auth.authentication.services;

import com.auth.authentication.dtos.PostDto;
import com.auth.authentication.exceptions.AppException;
import com.auth.authentication.exceptions.UserNotFoundException;
import com.auth.authentication.model.ApplicationUser;
import com.auth.authentication.model.Post;
import com.auth.authentication.model.Retweet;
import com.auth.authentication.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final RetweetRepository retweetRepository;

    @Transactional
    public void createPost(String username, PostDto postDto) throws IOException {
        ApplicationUser user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        Post post = new Post();

        post.setTextContent(postDto.getTextContent());
        post.setMediaContent(postDto.getMediaContent().getBytes());
        post.setPostDate(new Date());
        post.setAuthor(user);

        try {
            postRepository.save(post);
        } catch (DataIntegrityViolationException e) {
            throw new AppException("Error saving post to the database", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Page<Post> getUserOwnPosts(String username, int page, int size, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, "postDate"));

        return postRepository.findByAuthorUsername(username, pageRequest);
    }

    public Page<Post> getUserPostsById(Long userId, int page, int size, String direction) {
        try {
            Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, "postDate"));
            return postRepository.findByAuthorUserId(userId, pageRequest);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId);
        }
    }

    public Page<Post> getLikedPosts(String username, int page, int size, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, "postDate"));

        return postRepository.findLikedPosts(username, pageRequest);
    }

    public Page<Post> getBookmarkedPosts(String username, int page, int size, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, "postDate"));

        return postRepository.findBookmarkedPosts(username, pageRequest);
    }

    @Transactional
    public void deletePost(String username, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with ID: " + postId));

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this post");
        }

        commentRepository.deleteByPostId(postId);

        likeRepository.deleteByPostId(postId);

        postRepository.delete(post);
    }


    @Transactional
    public void retweetPost(Long postId, String username) {
        ApplicationUser retweeter = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        Post originalPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with ID: " + postId));

        // Check if the user has already retweeted this post
        boolean hasRetweeted = retweetRepository.existsByOriginalPostAndRetweeter(originalPost, retweeter);
        if (hasRetweeted) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have already retweeted this post");
        }

        // Check if the user is trying to retweet their own post
        if (originalPost.getAuthor().equals(retweeter)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot retweet your own post");
        }

        Retweet retweet = new Retweet(originalPost, retweeter, new Date());
        retweetRepository.save(retweet);
    }
}
