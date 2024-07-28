package com.example.myproject.domain.comment.service;

import com.example.myproject.domain.comment.dto.CommentCreateRequest;
import com.example.myproject.domain.user.repository.UserRepository;
import com.example.myproject.domain.comment.entity.Comment;
import com.example.myproject.domain.comment.repository.CommentRepository;
import com.example.myproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public Comment save(Long userId, CommentCreateRequest request, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found type id"));

        log.info("댓글 업로드 전 포스트잇 개수 = "+ user.getAvailablePostIt());
        user.PostItCountUpdate("commentUpload");
        log.info("댓글 업로드 후 포스트잇 개수 = "+ user.getAvailablePostIt());

        return commentRepository.save(request.toEntity(user.getId(), postId));
    }
}
