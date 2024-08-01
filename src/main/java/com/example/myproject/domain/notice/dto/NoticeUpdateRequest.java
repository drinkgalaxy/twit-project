package com.example.myproject.domain.notice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NoticeUpdateRequest {

    @Size(max = 200, message = "내용은 200자 이내로 입력해주세요.")
    @NotBlank
    private String noticeContents;
}
