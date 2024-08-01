package com.example.myproject.domain.comment.controller;

import com.example.myproject.common.security.CustomUserDetails;
import com.example.myproject.domain.comment.dto.CommentCreateRequest;
import com.example.myproject.domain.comment.dto.CommentResponse;
import com.example.myproject.domain.comment.entity.Comment;
import com.example.myproject.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentResponse> createComment(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                         @PathVariable Long postId,
                                                         @RequestBody @Valid CommentCreateRequest request) {
        Long userId = customUserDetails.getUserId();
        log.info("댓글 내용 = "+ request.getComments());
        Comment response = commentService.save(userId, request, postId);
        return ResponseEntity.ok(response.toResponse());

    }

    // 댓글 목록 조회
    @GetMapping("/comments/posts/{postId}")
    public ResponseEntity<List<CommentResponse>> findComments(@PathVariable Long postId) {
        List<Comment> comments = commentService.findComments(postId);
        List<CommentResponse> responses = comments.stream()
                .map(Comment::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    @PreAuthorize("isAuthenticated() && ((#customUserDetails.authorities.containsAll(@commentService.getPostAuthorAuth())) or (#customUserDetails.userId == @commentService.getPostAuthorId(#commentId)))")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                              @PathVariable Long commentId) {

        commentService.deleteComment(commentId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
