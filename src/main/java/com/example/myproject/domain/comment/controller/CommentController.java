package com.example.myproject.domain.comment.controller;

import com.example.myproject.common.security.CustomUserDetails;
import com.example.myproject.domain.comment.dto.CommentCreateRequest;
import com.example.myproject.domain.comment.dto.CommentResponse;
import com.example.myproject.domain.comment.entity.Comment;
import com.example.myproject.domain.comment.service.CommentService;
import com.example.myproject.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{postId}")
    public ResponseEntity<CommentResponse> createComment(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                         @PathVariable Long postId,
                                                         @RequestBody @Valid CommentCreateRequest request) {
        Long userId = customUserDetails.getUserId();
        Comment response = commentService.save(userId, request, postId);
        return ResponseEntity.ok(response.toResponse());

    }
}
