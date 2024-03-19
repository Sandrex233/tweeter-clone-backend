package com.auth.authentication.controller;

import com.auth.authentication.exceptions.AppException;
import com.auth.authentication.exceptions.DuplicateFollowException;
import com.auth.authentication.exceptions.FollowNotFoundException;
import com.auth.authentication.model.UserInfo;
import com.auth.authentication.services.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/follow")
@CrossOrigin("*")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping("/{userId}/followers")
    public ResponseEntity<Page<UserInfo>> getUserFollowers(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserInfo> followers = followService.getUserFollowers(userId, page, size);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<Page<UserInfo>> getUserFollowings(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserInfo> followings = followService.getUserFollowings(userId, page, size);
        return ResponseEntity.ok(followings);
    }

    @PostMapping("/{toFollowId}/follow")
    public ResponseEntity<String> followUser(@PathVariable Long toFollowId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        try {
            followService.followUser(username, toFollowId);
            return ResponseEntity.ok().body("User " + username + " follows " + toFollowId);
        } catch (DuplicateFollowException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{followedId}/unfollow")
    public ResponseEntity<String> unfollowUser(@PathVariable Long followedId,@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        try {
            followService.unfollowUser(username, followedId);
            return ResponseEntity.ok("User unfollowed successfully");
        } catch (FollowNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authenticated user not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while unfollowing user");
        }
    }
}
