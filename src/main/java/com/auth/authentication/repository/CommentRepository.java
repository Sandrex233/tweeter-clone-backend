package com.auth.authentication.repository;

import com.auth.authentication.dtos.CommentResponseDto;
import com.auth.authentication.model.Comment;
import com.auth.authentication.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPost(Post post, PageRequest pageRequest);

    void deleteByPostId(Long postId);
}
