package com.example.myproject.domain.comment.dto;

import com.example.myproject.domain.comment.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @NotBlank(message = "내용 입력은 필수입니다.")
    @Size(min = 50, max = 100, message = "내용은 50자 이상, 100자 이내로 입력해주세요.")
    private String comments;

    public Comment toEntity(Long userId, Long postId) {
        return Comment.builder()
                .userId(userId)
                .postId(postId)
                .comments(comments)
                .use_yn(true)
                .build();
    }
}
