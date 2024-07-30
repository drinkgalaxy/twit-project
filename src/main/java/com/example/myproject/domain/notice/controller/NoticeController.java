package com.example.myproject.domain.notice.controller;

import com.example.myproject.common.security.CustomUserDetails;
import com.example.myproject.domain.notice.dto.NoticeResponse;
import com.example.myproject.domain.notice.dto.NoticeUpdateRequest;
import com.example.myproject.domain.notice.entity.Notice;
import com.example.myproject.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    // 공지 조회
    @GetMapping("/notice/{noticeId}")
    public ResponseEntity<NoticeResponse> showNotice(@PathVariable Long noticeId) {
        return ResponseEntity
                .ok(noticeService.findNotice(noticeId));
    }

    // 공지 수정
    @PutMapping("/notice/{noticeId}")
    @PreAuthorize("#customUserDetails.authorities.containsAll(@postService.getPostAuthorAuth())")
    public ResponseEntity<NoticeResponse> updateNotice(@PathVariable Long noticeId,
                                                       @RequestBody NoticeUpdateRequest request,
                                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info(customUserDetails.getAuthorities().toString());
        Notice notice = noticeService.update(noticeId, request);
        return ResponseEntity
                .ok(notice.toResponse());
    }
}
