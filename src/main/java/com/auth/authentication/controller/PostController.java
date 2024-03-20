package com.auth.authentication.controller;

import com.auth.authentication.dtos.CommentResponseDto;
import com.auth.authentication.dtos.PostDto;
import com.auth.authentication.model.Comment;
import com.auth.authentication.model.Post;
import com.auth.authentication.repository.UserRepository;
import com.auth.authentication.services.CommentService;
import com.auth.authentication.services.LikeService;
import com.auth.authentication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/{userId}")
    public ResponseEntity<Page<Post>> getUserPostsById(@PathVariable Long userId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "DESC") String direction) {
            Page<Post> userPostsById = postService.getUserPostsById(userId, page, size, direction);
            return ResponseEntity.ok(userPostsById);
    }

    @GetMapping("/")
    public ResponseEntity<Page<Post>> getUserOwnPosts(@AuthenticationPrincipal Jwt jwt,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(defaultValue = "DESC") String direction) {
        String username = jwt.getSubject();

        Page<Post> userPosts = postService.getUserOwnPosts(username, page, size, direction);
        return ResponseEntity.ok(userPosts);
    }

    @GetMapping("/liked-posts")
    public ResponseEntity<Page<Post>> getLikedPosts(@AuthenticationPrincipal Jwt jwt,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "DESC") String direction) {

        String username = jwt.getSubject();
        Page<Post> userLikedPosts = postService.getLikedPosts(username, page, size, direction);

        return ResponseEntity.ok(userLikedPosts);
    }

    @GetMapping("/bookmarked-posts")
    public ResponseEntity<Page<Post>> getBookmarkedPosts(@AuthenticationPrincipal Jwt jwt,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "DESC") String direction) {

        String username = jwt.getSubject();
        Page<Post> userBookmarkedPosts = postService.getBookmarkedPosts(username, page, size, direction);

        return ResponseEntity.ok(userBookmarkedPosts);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<Page<CommentResponseDto>> getCommentsByPostId(@PathVariable Long postId,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size,
                                                                        @RequestParam(defaultValue = "DESC") String direction) {

        Page<CommentResponseDto> postComments = commentService.getCommentsByPostId(postId, page, size, direction);
        return ResponseEntity.ok(postComments);
    }

    @PostMapping("/")
    public ResponseEntity<String> createPost(@AuthenticationPrincipal Jwt jwt, @RequestBody @ModelAttribute PostDto postDto) throws IOException {
        String username = jwt.getSubject();
        try {
            postService.createPost(username, postDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{postId}")
    public ResponseEntity<String> retweetPost(@AuthenticationPrincipal Jwt jwt, @PathVariable Long postId) {
        String username = jwt.getSubject();
        try {
            postService.retweetPost(postId, username);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post retweeted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@AuthenticationPrincipal Jwt jwt, @PathVariable Long postId) {
        String username = jwt.getSubject();
        try {
            postService.deletePost(username, postId);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}
