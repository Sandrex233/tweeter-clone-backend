package com.auth.authentication.services;

import com.auth.authentication.dtos.EditDto;
import com.auth.authentication.dtos.UserInfoByIdWithFollowersDto;
import com.auth.authentication.dtos.UserInfoDto;
import com.auth.authentication.dtos.UserInfoWithFollowersDto;
import com.auth.authentication.exceptions.AppException;
import com.auth.authentication.model.ApplicationUser;
import com.auth.authentication.model.UserInfo;
import com.auth.authentication.repository.FollowRepository;
import com.auth.authentication.repository.UserInfoRepository;
import com.auth.authentication.repository.UserRepository;
import com.auth.authentication.utils.isValidPhotoUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final FollowRepository followRepository;

    public UserInfoDto getUserInfo(String username) {
        ApplicationUser user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        UserInfo userInfo = user.getUserInfo();
        UserInfoDto userInfoDto = new UserInfoDto();

        userInfoDto.setUserId(user.getUserId());
        userInfoDto.setEmail(user.getUsername());
        userInfoDto.setPassword(user.getPassword().toCharArray());

        if (userInfo != null) {
            userInfoDto.setName(userInfo.getName());
            userInfoDto.setBio(userInfo.getBio());
            userInfoDto.setNumber(userInfo.getNumber());
            userInfoDto.setProfilePic(userInfo.getProfilePic());
            userInfoDto.setCoverPic(userInfo.getCoverPic());
        }

        return userInfoDto;
    }

    public UserInfo getUserInfoById(Long userId) {
        ApplicationUser user = userRepository.findById(Math.toIntExact(userId)).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if(user == null) {
            throw new EntityNotFoundException("User not found for ID: " + userId);
        }

        return user.getUserInfo();
    }

    @Transactional
    public void uploadUserInfo(String username, EditDto editDto) throws IOException {
        ApplicationUser user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        UserInfo userInfo = new UserInfo();
        if (isValidPhotoUtil.isValidPhoto(editDto.getProfilePhoto())) {
            userInfo.setProfilePic(editDto.getProfilePhoto().getBytes());
        } else {
            throw new AppException("Invalid profile photo format. Only image files are allowed.", HttpStatus.BAD_REQUEST);
        }
        if (isValidPhotoUtil.isValidPhoto(editDto.getCoverPhoto())) {
            userInfo.setCoverPic(editDto.getCoverPhoto().getBytes());
        } else {
            throw new AppException("Invalid profile photo format. Only image files are allowed.", HttpStatus.BAD_REQUEST);
        }

        userInfo.setName(editDto.getName());
        userInfo.setBio(editDto.getBio());
        userInfo.setNumber(editDto.getNumber());
        userInfo.setUser(user);

        userInfoRepository.save(userInfo);
    }

    public void updateUserInfo(String username, EditDto editDto) throws IOException {
        ApplicationUser user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        UserInfo userInfo = user.getUserInfo();

        if(userInfo == null) {
            throw new EntityNotFoundException("User info not found for user with email: " + username);
        }

        userInfo.setProfilePic(editDto.getProfilePhoto().getBytes());
        userInfo.setCoverPic(editDto.getCoverPhoto().getBytes());
        userInfo.setName(editDto.getName());
        userInfo.setBio(editDto.getBio());
        userInfo.setNumber(editDto.getNumber());

        userInfoRepository.save(userInfo);
    }


    public UserInfoWithFollowersDto getUserInfoWithCounts(String username) {
        UserInfoDto userInfo = getUserInfo(username);
        log.info("User's id is {}", userInfo.getUserId());
        long followerCount = followRepository.countFollowById(Long.valueOf(userInfo.getUserId()));
        long followingCount = followRepository.countFollowsById(Long.valueOf(userInfo.getUserId()));
        return new UserInfoWithFollowersDto(userInfo, followerCount, followingCount);
    }

    public UserInfoByIdWithFollowersDto getUserInfoByIdWithCounts(Long userId) {
        ApplicationUser user = userRepository.findById(Math.toIntExact(userId)).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if(user == null) {
            throw new EntityNotFoundException("User not found for ID: " + userId);
        }

        UserInfo userInfo = user.getUserInfo();

        long followerCount = followRepository.countFollowById(userId);
        long followingCount = followRepository.countFollowsById(userId);
        return new UserInfoByIdWithFollowersDto(userInfo, followerCount, followingCount);
    }
}
