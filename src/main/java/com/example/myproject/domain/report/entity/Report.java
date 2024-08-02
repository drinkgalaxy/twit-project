package com.example.myproject.domain.report.entity;

import com.example.myproject.common.base.BaseTimeEntity;
import com.example.myproject.domain.report.dto.ReportResponse;
import com.example.myproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    private Long commentId; // 여기서 신고당한 userId 겟

    private Long userId; // 신고한 유저의 아이디

    private boolean use_yn;

    private String reportReason;

    @Builder
    public Report(Long id, Long commentId, Long userId, boolean use_yn, String reportReason) {
        this.id = id;
        this.commentId = commentId;
        this.userId = userId;
        this.use_yn = use_yn;
        this.reportReason = reportReason;
    }

    public ReportResponse toResponse() {
        return ReportResponse.builder()
                .id(id)
                .commentId(commentId)
                .userId(userId)
                .use_yn(use_yn)
                .reportReason(reportReason)
                .createdDate(getCreatedDate())
                .build();
    }

    public void check() {
        this.use_yn = false;
    }
}
