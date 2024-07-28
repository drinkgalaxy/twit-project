package com.example.myproject.domain.post.dto;

import com.example.myproject.domain.post.entity.Post;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class PostCreateRequest {

    @NotBlank(message = "제목 입력은 필수입니다.")
    private String title;

    @NotBlank(message = "내용 입력은 필수입니다.")
    private String contents;

    @NotBlank(message = "한줄소개 입력은 필수입니다.")
    private String description;

    public Post toEntity(Long userId, String nickname) {
        return Post.builder()
                .userId(userId)
                .title(title)
                .contents(contents)
                .description(description)
                .viewCount(0L)
                .use_yn(true)
                .createdBy(nickname)
                .lastModifiedBy(nickname)
                .build();
    }
}
