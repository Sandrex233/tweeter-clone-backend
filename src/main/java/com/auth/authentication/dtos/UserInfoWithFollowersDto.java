package com.auth.authentication.dtos;

import com.auth.authentication.model.UserInfo;

public class UserInfoWithFollowersDto {
    private UserInfoDto userInfoDto;
    private long followerCount;
    private long followingCount;

    public UserInfoWithFollowersDto() {
    }

    public UserInfoWithFollowersDto(UserInfoDto userInfoDto, long followerCount, long followingCount) {
        this.userInfoDto = userInfoDto;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

    public UserInfoDto getUserInfoDto() {
        return userInfoDto;
    }

    public void setUserInfoDto(UserInfoDto userInfoDto) {
        this.userInfoDto = userInfoDto;
    }

    public long getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(long followerCount) {
        this.followerCount = followerCount;
    }

    public long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(long followingCount) {
        this.followingCount = followingCount;
    }
}
