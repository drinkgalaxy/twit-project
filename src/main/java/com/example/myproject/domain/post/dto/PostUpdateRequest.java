package com.example.myproject.domain.post.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class PostUpdateRequest {

    @Size(max = 1000, message = "내용은 1000자 이내로 입력해주세요.")
    private String contents;

    @Size(max = 250, message = "내용은 250자 이내로 입력해주세요.")
    private String description;

    @Nullable
    private MultipartFile attachFile;

}
