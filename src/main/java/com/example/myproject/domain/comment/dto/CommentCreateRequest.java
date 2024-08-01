package com.example.myproject.domain.comment.dto;

import com.example.myproject.domain.comment.entity.Comment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @Size(min = 50, max = 250, message = "내용은 50자 이상, 250자 이내로 입력해주세요.")
    private String comments;

    public Comment toEntity(Long userId, Long postId, String comments, String nickname) {
        return Comment.builder()
                .userId(userId)
                .postId(postId)
                .comments(comments)
                .createdBy(nickname)
                .use_yn(true)
                .build();
    }
}
