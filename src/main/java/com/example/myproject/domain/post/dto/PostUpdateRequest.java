package com.example.myproject.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostUpdateRequest {

    @Size(max = 1000, message = "내용은 1000자 이내로 입력해주세요.")
    private String contents;

    @Size(max = 250, message = "내용은 250자 이내로 입력해주세요.")
    private String description;
}
