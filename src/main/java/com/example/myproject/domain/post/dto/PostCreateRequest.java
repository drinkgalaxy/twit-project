package com.example.myproject.domain.post.dto;

import com.example.myproject.domain.post.entity.Post;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostCreateRequest {

    private String title;

    @Size(max = 250, message = "한줄소개는 250자 이내로 입력해주세요.")
    private String description;

    @Size(max = 1000, message = "내용은 1000자 이내로 입력해주세요.")
    private String contents;


    public Post toEntity(Long userId, String nickname, String originalFileName) {
        return Post.builder()
                .userId(userId)
                .title(title)
                .description(description)
                .contents(contents)
                .viewCount(0L)
                .use_yn(true)
                .createdBy(nickname)
                .lastModifiedBy(nickname)
                .fileName(originalFileName)
                .build();
    }
}
