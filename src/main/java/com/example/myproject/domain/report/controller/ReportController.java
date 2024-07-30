package com.example.myproject.domain.report.controller;

import com.example.myproject.common.security.CustomUserDetails;
import com.example.myproject.domain.report.dto.ReportCreateRequest;
import com.example.myproject.domain.report.dto.ReportResponse;
import com.example.myproject.domain.report.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;

    // 댓글 신고 생성
    @PostMapping("/report/comments/{commentId}")
    public ResponseEntity<ReportResponse> createReport(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                       @PathVariable Long commentId,
                                                       @RequestBody @Valid ReportCreateRequest request) {
        Long userId = customUserDetails.getUserId();
        ReportResponse response = reportService.create(userId, commentId, request);

        return ResponseEntity.status(CREATED)
                .body(response);
    }

    // 신고 목록 조회
    @GetMapping("/report/comments")
    public ResponseEntity<List<ReportResponse>> findReports() {
        List<ReportResponse> responses = reportService.findReports();
        return ResponseEntity.ok(responses);
    }

    // 신고 수락
    @PatchMapping("/report/{reportId}/accept")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> acceptReport(@PathVariable Long reportId) {
        reportService.acceptReport(reportId);

        return ResponseEntity.ok().build();
    }

    // 신고 거절
    @DeleteMapping("/report/{reportId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId) {
        reportService.deleteReport(reportId);

        return ResponseEntity.noContent().build();
    }





}
