package com.auth.authentication.controller;

import com.auth.authentication.services.BookmarkService;
import com.auth.authentication.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/{postId}/bookmark")
    public ResponseEntity<String> bookmarkPost(@PathVariable Long postId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        bookmarkService.bookmarkPost(username, postId);
        return ResponseEntity.ok("Post bookmarked successfully");
    }

    @DeleteMapping("/{postId}/unbookmark")
    public ResponseEntity<String> unbookmarkPost(@PathVariable Long postId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        bookmarkService.unbookmarkPost(username, postId);
        return ResponseEntity.ok("Post unbookmarked successfully");
    }

}
