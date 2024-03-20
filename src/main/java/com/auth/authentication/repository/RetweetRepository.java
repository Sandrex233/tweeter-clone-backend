package com.auth.authentication.repository;

import com.auth.authentication.model.ApplicationUser;
import com.auth.authentication.model.Post;
import com.auth.authentication.model.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet, Long> {
    boolean existsByOriginalPostAndRetweeter(Post originalPost, ApplicationUser retweeter);
}
