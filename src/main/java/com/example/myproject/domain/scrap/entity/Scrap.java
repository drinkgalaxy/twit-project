package com.example.myproject.domain.scrap.entity;

import com.example.myproject.common.base.BaseTimeEntity;
import com.example.myproject.domain.post.dto.PostResponse;
import com.example.myproject.domain.scrap.dto.ScrapListResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor

public class Scrap extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long id;

    private Long postId;

    private Long userId;

    public Scrap(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    // Scrap 객체를 받아서 ScrapListResponse 를 생성
    public ScrapListResponse toResponse() {
        return ScrapListResponse.builder()
                .id(id)
                .postId(postId)
                .userId(userId)
                .createdDate(getCreatedDate())
                .build();
    }
}
