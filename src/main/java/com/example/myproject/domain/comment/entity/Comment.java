package com.example.myproject.domain.comment.entity;

import com.example.myproject.common.base.BaseTimeEntity;
import com.example.myproject.domain.comment.dto.CommentResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private Long postId;

    private Long userId;

    private String comments;

    private boolean use_yn;

    @Builder
    public Comment(Long id, Long postId, Long userId, String comments, boolean use_yn) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.comments = comments;
        this.use_yn = use_yn;
    }

    // Comment 객체를 받아서 CommentResponse 생성
    public CommentResponse toResponse() {
        return CommentResponse
                .builder()
                .id(id)
                .postId(id)
                .userId(userId)
                .comments(comments)
                .use_yn(use_yn)
                .createdDate(getCreatedDate())
                .build();
    }

}
