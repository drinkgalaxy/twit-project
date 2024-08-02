package com.example.myproject.domain.scrap.service;

import com.example.myproject.domain.comment.dto.CommentResponse;
import com.example.myproject.domain.comment.entity.Comment;
import com.example.myproject.domain.scrap.dto.ScrapListResponse;
import com.example.myproject.domain.scrap.dto.ScrapMessageResponse;
import com.example.myproject.domain.scrap.entity.Scrap;
import com.example.myproject.domain.scrap.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ScrapService {

    private final ScrapRepository scrapRepository;

    public ScrapMessageResponse updateScrap(Long postId, Long userId) {

        if (!hasScrap(postId, userId)) {
            createScrap(postId, userId);
            return ScrapMessageResponse.builder().message("게시글이 스크랩 목록에 추가되었습니다.").build();
        } else {
            deleteScrap(postId, userId);
            return ScrapMessageResponse.builder().message("스크랩이 취소되었습니다.").build();
        }
    }

    public boolean hasScrap(Long postId, Long userId) {
        return scrapRepository.findByPostIdAndUserId(postId, userId).isPresent();
    }

    private void createScrap(Long postId, Long userId) {
        Scrap scrap = new Scrap(postId, userId);
        scrapRepository.save(scrap);
    }

    private void deleteScrap(Long postId, Long userId) {
        Scrap scrap = (Scrap) scrapRepository.findByPostIdAndUserId(postId, userId)
                        .orElseThrow(() -> new IllegalArgumentException("찾으려는 스크랩이 존재하지 않습니다."));
        scrapRepository.delete(scrap);
    }
}
