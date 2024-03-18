package com.auth.authentication.dtos;

import com.auth.authentication.model.UserInfo;

public class UserInfoByIdWithFollowersDto {
    private UserInfo userInfo;
    private long followerCount;
    private long followingCount;

    public UserInfoByIdWithFollowersDto() {
    }

    public UserInfoByIdWithFollowersDto(UserInfo userInfo, long followerCount, long followingCount) {
        this.userInfo = userInfo;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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
