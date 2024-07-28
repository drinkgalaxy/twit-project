package com.example.myproject.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Long id;

    private Long postId;

    private Long userId;

    private String comments;

    private boolean use_yn;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
