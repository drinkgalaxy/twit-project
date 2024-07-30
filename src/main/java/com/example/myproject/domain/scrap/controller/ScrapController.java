package com.example.myproject.domain.scrap.controller;

import com.example.myproject.common.security.CustomUserDetails;
import com.example.myproject.domain.scrap.dto.ScrapListResponse;
import com.example.myproject.domain.scrap.dto.ScrapMessageResponse;
import com.example.myproject.domain.scrap.entity.Scrap;
import com.example.myproject.domain.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ScrapController {

    private final ScrapService scrapService;

    // 스크랩 상태 업데이트
    @PostMapping("/post/{postId}/scrap")
    public ResponseEntity<ScrapMessageResponse> updateScrap(@PathVariable Long postId,
                                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUserId();
        ScrapMessageResponse response = scrapService.updateScrap(postId, userId);

        return ResponseEntity.ok().body(response);
    }

    // 스크랩 상태 조회
    @GetMapping("/post/{postId}/scrap/status")
    public boolean getScrapStatus(@PathVariable Long postId,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUserId();
        return scrapService.hasScrap(postId, userId);
    }

    // 유저 당 스크랩 한 게시글 목록 조회
    @GetMapping("/post/scraps")
    public List<ScrapListResponse> findScraps(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUserId();
        return scrapService.findScraps(userId);
    }
}
