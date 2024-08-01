package com.example.myproject.domain.notice.service;

import com.example.myproject.domain.notice.dto.NoticeResponse;
import com.example.myproject.domain.notice.dto.NoticeUpdateRequest;
import com.example.myproject.domain.notice.entity.Notice;
import com.example.myproject.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeResponse findNotice() {
        Notice notice = noticeRepository.findNotice();
        return notice.toResponse();
    }

    public Notice update(Long noticeId, NoticeUpdateRequest request) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("noticeId 를 찾을 수 없습니다."));

        notice.update(request.getNoticeContents());

        return notice;
    }
}
