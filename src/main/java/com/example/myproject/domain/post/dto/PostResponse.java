package com.example.myproject.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;

    private Long userId;

    private String title;

    private String contents;

    private String description;

    private Long viewCount;

    private boolean use_yn;

    private String createdBy;

    private String lastModifiedBy;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
