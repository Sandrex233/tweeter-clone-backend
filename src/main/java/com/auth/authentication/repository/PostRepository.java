package com.auth.authentication.repository;

import com.auth.authentication.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByAuthorUsername(String username, PageRequest pageRequest);
    @Query("SELECT p FROM Post p WHERE p.author.userId = :userId")
    Page<Post> findByAuthorUserId(@Param("userId") Long userId, PageRequest pageRequest);

    @Query("SELECT p FROM Post p WHERE p.id IN (SELECT l.post.id FROM Like l WHERE l.user.username = :username)")
    Page<Post> findLikedPosts(@Param("username") String username, PageRequest pageRequest);

    @Query("SELECT p FROM Post p WHERE p.id IN (SELECT b.post.id FROM Bookmark b WHERE b.user.username = :username)")
    Page<Post> findBookmarkedPosts(@Param("username") String username, PageRequest pageRequest);
}
