package com.auth.authentication.services;

import com.auth.authentication.dtos.CommentRequestDto;
import com.auth.authentication.dtos.CommentResponseDto;
import com.auth.authentication.dtos.UserInfoDto;
import com.auth.authentication.exceptions.AppException;
import com.auth.authentication.model.ApplicationUser;
import com.auth.authentication.model.Comment;
import com.auth.authentication.model.CommentLike;
import com.auth.authentication.model.Post;
import com.auth.authentication.repository.CommentLikeRepository;
import com.auth.authentication.repository.CommentRepository;
import com.auth.authentication.repository.PostRepository;
import com.auth.authentication.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public Comment getCommentById(Long commentId) throws EntityNotFoundException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment with Id " + commentId + " not found"));
        return comment;
    }

    public Page<CommentResponseDto> getCommentsByPostId(Long postId, int page, int size, String direction) throws EntityNotFoundException {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, size, sortDirection, "dateReplied");

        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        Page<Comment> commentsPage = commentRepository.findByPost(post, pageRequest);


        return commentsPage.map(comment -> {
            UserInfoDto authorDto = new UserInfoDto(
                    comment.getAuthor().getUserId(),
                    comment.getAuthor().getUserInfo().getName(),
                    comment.getAuthor().getUserInfo().getProfilePic()
            );
            return new CommentResponseDto(comment.getId(), comment.getContent(), comment.getDateReplied(), authorDto.getUserId(), authorDto.getName(), authorDto.getProfilePic());
        });

    }

    @Transactional
    public Comment replyToPost(String username, CommentRequestDto commentRequestDto) {
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        Long postId = commentRequestDto.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with ID: " + postId));

        Comment comment = new Comment();
        comment.setContent(commentRequestDto.getContent());
        comment.setAuthor(user);
        comment.setPost(post);
        comment.setDateReplied(new Date());

        return commentRepository.save(comment);
    }

    @Transactional
    public void likeComment(Long commentId, String username) {
        // Check if the user has already liked the comment
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment with Id " + commentId + " not found"));
        ApplicationUser user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        boolean alreadyLiked = commentLikeRepository.existsByUserAndComment(user, comment);
        if (alreadyLiked) {
            throw new AppException("User has already liked this post", HttpStatus.BAD_REQUEST);
        }

        // Create a new CommentLike entity and save it
        CommentLike commentLike = new CommentLike();
        commentLike.setComment(comment);
        commentLike.setUser(user);
        commentLikeRepository.save(commentLike);
    }


    @Transactional
    public void unlikeComment(Long commentId, String username) {
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + commentId));

        CommentLike like = commentLikeRepository.findByCommentAndUser(comment, user)
                .orElseThrow(() -> new AppException("User has not liked this comment", HttpStatus.BAD_REQUEST));

        commentLikeRepository.delete(like);
    }
}
