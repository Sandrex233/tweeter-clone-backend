package com.auth.authentication.repository;

import com.auth.authentication.model.ApplicationUser;
import com.auth.authentication.model.Comment;
import com.auth.authentication.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentAndUser(Comment comment, ApplicationUser user);
    boolean existsByUserAndComment(ApplicationUser user, Comment comment);
}
