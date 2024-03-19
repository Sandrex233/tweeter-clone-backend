package com.auth.authentication.controller;

import com.auth.authentication.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@CrossOrigin("*")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        likeService.likePost(username, postId);
        return ResponseEntity.ok("Post liked successfully");
    }

    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        likeService.unlikePost(username, postId);
        return ResponseEntity.ok("Post unliked successfully");
    }
}
