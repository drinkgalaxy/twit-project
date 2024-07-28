package com.example.myproject.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostUpdateRequest {

    @NotBlank(message = "내용 입력은 필수입니다.")
    private String contents;

    @NotBlank(message = "한줄소개 입력은 필수입니다.")
    private String description;

}
