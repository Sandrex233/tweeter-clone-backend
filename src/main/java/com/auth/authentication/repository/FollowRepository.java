package com.auth.authentication.repository;

import com.auth.authentication.model.ApplicationUser;
import com.auth.authentication.model.Follow;
import com.auth.authentication.model.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Follow f WHERE f.follower.userId = :followerId AND f.followed.userId = :followedId")
    boolean existsByFollowerIdAndFollowedId(@Param("followerId") Long followerId, @Param("followedId") Long followedId);

    @Modifying
    @Query("DELETE FROM Follow f WHERE f.follower.userId = :followerId AND f.followed.userId = :followedId")
    void deleteByFollowerIdAndFollowedId(@Param("followerId") Long followerId, @Param("followedId") Long followedId);

    @Query("SELECT f.follower.userInfo FROM Follow f WHERE f.followed.userId = :followedId")
    Page<UserInfo>  findFollowersByFollowedId(@Param("followedId") Long followedId, Pageable pageable);

    @Query("SELECT f.followed.userInfo FROM Follow f WHERE f.follower.userId = :followingId")
    Page<UserInfo> findFollowingByFollowerId(@Param("followingId") Long followingId, Pageable pageable);

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.followed.userId = :id")
    long countFollowById(@Param("id") Long id);

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.follower.userId = :id")
    long countFollowsById(@Param("id") Long id);
}
