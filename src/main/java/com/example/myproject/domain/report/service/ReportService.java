package com.example.myproject.domain.report.service;

import com.example.myproject.domain.comment.entity.Comment;
import com.example.myproject.domain.comment.repository.CommentRepository;
import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.post.repository.PostRepository;
import com.example.myproject.domain.report.dto.ReportCreateRequest;
import com.example.myproject.domain.report.dto.ReportResponse;
import com.example.myproject.domain.report.entity.Report;
import com.example.myproject.domain.report.repository.ReportRepository;
import com.example.myproject.domain.user.dto.UserResponse;
import com.example.myproject.domain.user.entity.User;
import com.example.myproject.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public ReportResponse create(Long userId, Long commentId, ReportCreateRequest request) {
        Report report = request.toEntity(userId, commentId);
        Report savedReport = reportRepository.save(report);

        // 신고한 순간 use_yn은 false 로 변경
        Comment comment = commentRepository.findByIdAble(commentId);
        commentRepository.disableCommentById(comment.getId());

        return savedReport.toResponse();

    }

    public List<ReportResponse> findReports() {
        List<Report> reports = reportRepository.findAllAble();

        return reports.stream().map(report -> {
            Comment comment = commentRepository.findById(report.getCommentId()).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
            User user = userRepository.findById(comment.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Post post = postRepository.findByIdAble(comment.getPostId());
            return ReportResponse.builder()
                    .id(report.getId())
                    .commentId(report.getCommentId())
                    .reportedComments(comment.getComments())
                    .userId(report.getUserId())
                    .postId(post.getId())
                    .ReportedNickname(user.getNickname())
                    .use_yn(report.isUse_yn())
                    .reportReason(report.getReportReason())
                    .createdDate(report.getCreatedDate())
                    .build();
        }).collect(Collectors.toList());
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
