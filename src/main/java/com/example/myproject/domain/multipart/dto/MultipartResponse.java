package com.example.myproject.domain.multipart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Builder
public class MultipartResponse {

    private Long id;

    private Long postId;

    private String originalFileName;

    private String storedFileName;
}
