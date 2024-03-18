package com.auth.authentication.repository;

import com.auth.authentication.model.ApplicationUser;
import com.auth.authentication.model.Like;
import com.auth.authentication.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndPost(ApplicationUser user, Post post);

    boolean existsByUserAndPost(ApplicationUser user, Post post);
}
