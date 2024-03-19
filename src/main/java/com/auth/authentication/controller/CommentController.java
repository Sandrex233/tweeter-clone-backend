package com.auth.authentication.controller;

import com.auth.authentication.dtos.CommentRequestDto;
import com.auth.authentication.model.Comment;
import com.auth.authentication.services.CommentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) throws EntityNotFoundException {
        Comment comment = commentService.getCommentById(commentId);

        return ResponseEntity.ok(comment);
    }

    @PostMapping("/")
    public ResponseEntity<Comment> replyToPost(@RequestBody @Valid CommentRequestDto commentRequestDto, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        Comment createdComment = commentService.replyToPost(username, commentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<String> likeComment(@PathVariable Long commentId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();

        commentService.likeComment(commentId, username);
        return ResponseEntity.ok("Comment liked successfully");
    }

    @DeleteMapping("/{commentId}/unlike")
    public ResponseEntity<String> unlikeComment(@PathVariable Long commentId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        commentService.unlikeComment(commentId, username);
        return ResponseEntity.ok("Comment unliked successfully");
    }
}
