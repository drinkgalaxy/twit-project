package com.example.myproject.domain.post.controller;

import com.example.myproject.common.security.CustomUserDetails;
import com.example.myproject.domain.multipart.service.LocalFileStorageService;
import com.example.myproject.domain.multipart.entity.Multipart;
import com.example.myproject.domain.post.dto.PostCreateRequest;
import com.example.myproject.domain.post.dto.PostResponse;
import com.example.myproject.domain.post.dto.PostUpdateRequest;
import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @PostMapping("/posts")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @ModelAttribute @Valid PostCreateRequest request,
                                        @RequestPart("file") MultipartFile file) {
        Long userId = customUserDetails.getUserId();

        try {
            Post post = postService.save(request, userId, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(post.toResponse());
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 게시글 상세
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> showOnePost(@PathVariable Long postId) {
        return ResponseEntity
                .ok(postService.findOnePost(postId));
    }

    // 게시글 수정
    @PutMapping("/posts/{postId}")
    @PreAuthorize("isAuthenticated() and ((#customUserDetails != null and #customUserDetails.authorities.containsAll(@postService.getPostAuthorAuth())) or (#customUserDetails != null and #customUserDetails.userId == @postService.getPostAuthorId(#postId)))")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId,
                                                   @ModelAttribute @Valid PostUpdateRequest request,
                                                   @RequestPart("file") MultipartFile file,
                                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {

        Post updatedPost = postService.update(postId, request, file);

        return ResponseEntity
                .ok(updatedPost.toResponse());
    }

    // 게시글 삭제 - 사실 삭제 대신 use_yn = false 처리
    @DeleteMapping("/posts/{postId}")
    @PreAuthorize("isAuthenticated() and ((#customUserDetails != null and #customUserDetails.authorities.containsAll(@postService.getPostAuthorAuth())) or (#customUserDetails != null and #customUserDetails.userId == @postService.getPostAuthorId(#postId)))")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId,
                                           @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        postService.disablePostById(postId);
        return ResponseEntity
                .ok().build();
    }

}
