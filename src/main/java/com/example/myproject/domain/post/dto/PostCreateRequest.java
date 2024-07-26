package com.example.myproject.domain.post.dto;

import com.example.myproject.domain.post.entity.Post;
import lombok.Data;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    private String title;

    private String contents;

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
