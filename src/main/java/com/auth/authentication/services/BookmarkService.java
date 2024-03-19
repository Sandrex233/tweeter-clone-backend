package com.auth.authentication.services;

import com.auth.authentication.exceptions.AppException;
import com.auth.authentication.model.ApplicationUser;
import com.auth.authentication.model.Bookmark;
import com.auth.authentication.model.Post;
import com.auth.authentication.repository.BookmarkRepository;
import com.auth.authentication.repository.PostRepository;
import com.auth.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void bookmarkPost(String username, Long postId) {
        ApplicationUser user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException("Post with an Id: " + postId + " not found", HttpStatus.NOT_FOUND));

        boolean alreadyBookmarked = bookmarkRepository.existsByUserAndPost(user, post);
        if (alreadyBookmarked) {
            throw new AppException("User has already bookmarked this post", HttpStatus.BAD_REQUEST);
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setPost(post);

        bookmarkRepository.save(bookmark);
    }

    public void unbookmarkPost(String username, Long postId) {
        ApplicationUser user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException("Post with an Id: " + postId + " not found", HttpStatus.NOT_FOUND));

        Bookmark bookmark = bookmarkRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new AppException("User has not bookmarked this post", HttpStatus.BAD_REQUEST));

        bookmarkRepository.delete(bookmark);
    }


}
