package com.auth.authentication.controller;

import com.auth.authentication.dtos.EditDto;
import com.auth.authentication.dtos.UserInfoByIdWithFollowersDto;
import com.auth.authentication.dtos.UserInfoDto;
import com.auth.authentication.dtos.UserInfoWithFollowersDto;
import com.auth.authentication.model.UserInfo;
import com.auth.authentication.services.AuthenticationService;
import com.auth.authentication.services.UserInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserInfoService userInfoService;
    private final AuthenticationService authenticationService;

    @GetMapping("/")
    public ResponseEntity<UserInfoDto> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        return ResponseEntity.ok(userInfoService.getUserInfo(username));
    }

    @GetMapping("/counts")
    public ResponseEntity<UserInfoWithFollowersDto> getUserInfoWithCounts(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        return ResponseEntity.ok(userInfoService.getUserInfoWithCounts(username));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getUserInfoById(@PathVariable Long userId) {
        return ResponseEntity.ok(userInfoService.getUserInfoById(userId));
    }

    @GetMapping("/{userId}/counts")
    public ResponseEntity<UserInfoByIdWithFollowersDto> getUserInfoByIdWithCounts(@PathVariable Long userId) {
        return ResponseEntity.ok(userInfoService.getUserInfoByIdWithCounts(userId));
    }

    @PostMapping("/")
    public ResponseEntity<String> uploadUserInfo(@AuthenticationPrincipal Jwt jwt, @RequestBody @ModelAttribute @Valid EditDto editDto) throws IOException {
        String username = jwt.getSubject(); // Extract username from JWT token
        userInfoService.uploadUserInfo(username, editDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(username + "User Info Uploaded successfully");
    }

    @PutMapping("/")
    public ResponseEntity<String> updateUserInfo(@AuthenticationPrincipal Jwt jwt, @RequestBody @ModelAttribute @Valid EditDto editDto) throws IOException {
        String username = jwt.getSubject();
        userInfoService.updateUserInfo(username, editDto);
        return ResponseEntity.ok().body(username + "User Info updated successfully");
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        return ResponseEntity.ok(authenticationService.deleteLoggedInUser(username));
    }
}
