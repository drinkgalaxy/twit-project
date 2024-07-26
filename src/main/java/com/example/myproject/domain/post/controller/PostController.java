package com.example.myproject.domain.post.controller;

import com.example.myproject.common.security.CustomUserDetails;
import com.example.myproject.domain.post.dto.PostCreateRequest;
import com.example.myproject.domain.post.dto.PostResponse;
import com.example.myproject.domain.post.dto.PostUpdateRequest;
import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @PostMapping("/posts")
    // @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<PostResponse> createPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                   @RequestBody PostCreateRequest request) {
        Long userId = customUserDetails.getUserId();
        Post post = postService.save(request, userId);
        // TODO 파일 업로드 기능 추가
        log.info("createdDate = "+post.toResponse().getCreatedDate());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(post.toResponse());
    }

    // 게시글 상세
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> showOnePost(@PathVariable Long postId) {
        return ResponseEntity
                .ok(postService.findOnePost(postId));
    }

    // 게시글 목록 조회
    // todo 페이징 기능 - 3개씩 보여주기

    // 게시글 수정
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId,
                                                   @RequestBody PostUpdateRequest request) {
        Post updatedPost = postService.update(postId, request);
        return ResponseEntity
                .ok(updatedPost.toResponse());
    }

    // 모집글 삭제 - 사실 삭제 대신 use_yn = false 처리
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.disablePostById(postId);
        return ResponseEntity
                .ok().build();
    }


}
