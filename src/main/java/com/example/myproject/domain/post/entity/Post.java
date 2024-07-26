package com.example.myproject.domain.post.entity;

import com.example.myproject.common.base.BaseTimeEntity;
import com.example.myproject.domain.post.dto.PostResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private Long userId;

    private String title;

    private String contents;

    private String description;

    private Long viewCount;

    private boolean use_yn;

    private String createdBy;

    private String lastModifiedBy;

    @Builder
    public Post(Long id, Long userId, String title, String contents, String description, Long viewCount
            , boolean use_yn, String createdBy, String lastModifiedBy) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.description = description;
        this.viewCount = viewCount;
        this.use_yn = use_yn;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    // Post 객체를 받아서 PostResponse 를 생성
    public PostResponse toResponse() {
        return PostResponse
                .builder()
                .id(id)
                .userId(userId)
                .title(title)
                .contents(contents)
                .description(description)
                .viewCount(viewCount)
                .use_yn(use_yn)
                .createdBy(createdBy)
                .lastModifiedBy(lastModifiedBy)
                .createdDate(getCreatedDate())
                .lastModifiedDate(getLastModifiedDate())
                .build();
    }

    public void update(String contents, String description) {
        this.contents = contents;
        this.description = description;
    }
}
