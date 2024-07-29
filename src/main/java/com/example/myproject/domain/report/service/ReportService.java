package com.example.myproject.domain.report.service;

import com.example.myproject.domain.comment.entity.Comment;
import com.example.myproject.domain.comment.repository.CommentRepository;
import com.example.myproject.domain.report.dto.ReportCreateRequest;
import com.example.myproject.domain.report.dto.ReportResponse;
import com.example.myproject.domain.report.entity.Report;
import com.example.myproject.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final CommentRepository commentRepository;

    public ReportResponse create(Long userId, Long commentId, ReportCreateRequest request) {
        Report report = request.toEntity(userId, commentId);
        Report savedReport = reportRepository.save(report);

        // 신고한 순간 use_yn은 false 로 변경
        Comment comment = commentRepository.findByIdAble(commentId);
        commentRepository.disableCommentById(comment.getId());

        return savedReport.toResponse();

    }

    public List<ReportResponse> findReports() {
        List<Report> findReports = reportRepository.findAllAble();

        if (findReports.isEmpty()) {
            throw new IllegalArgumentException("신고된 리뷰가 없습니다.");
        }

        return findReports.stream()
                .map(Report::toResponse)
                .toList();
    }

    public void acceptReport(Long reportId) {
        Report findReport = reportRepository.findByIdAble(reportId);
        findReport.check();
    }

    public void deleteReport(Long reportId) {
        Report findReport = reportRepository.findByIdAble(reportId);
        findReport.check();

        Long commentId = findReport.getCommentId();
        commentRepository.ableCommentById(commentId);
    }
}
