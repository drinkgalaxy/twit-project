package com.example.myproject.domain.report.dto;

import com.example.myproject.domain.report.entity.Report;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReportCreateRequest {

    @NotBlank(message = "내용 입력은 필수입니다.")
    @Size(max = 50, message = "내용은 50자 이내로 입력해주세요.")
    private String reportReason;

    public Report toEntity(Long userId, Long commentId) {
        return Report.builder()
                .commentId(commentId)
                .userId(userId)
                .use_yn(true)
                .reportReason(reportReason)
                .build();
    }
}
