package com.auth.authentication.repository;

import com.auth.authentication.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository  extends JpaRepository<UserInfo, Long> {
}
