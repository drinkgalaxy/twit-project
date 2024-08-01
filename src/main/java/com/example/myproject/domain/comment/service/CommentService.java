package com.example.myproject.domain.comment.service;

import com.example.myproject.domain.comment.dto.CommentCreateRequest;
import com.example.myproject.domain.comment.dto.CommentResponse;
import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.post.repository.PostRepository;
import com.example.myproject.domain.user.repository.UserRepository;
import com.example.myproject.domain.comment.entity.Comment;
import com.example.myproject.domain.comment.repository.CommentRepository;
import com.example.myproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

        return commentRepository.save(request.toEntity(user.getId(), postId, request.getComments(), user.getNickname()));
    }

    public List<Comment> findComments(Long postId) {
        return commentRepository.findAllById(postId);
    }

    public void deleteComment(Long commentId) {
        Comment findComment = commentRepository.findByIdAble(commentId);
        commentRepository.disableCommentById(findComment.getId());
    }


    // 특정 게시글의 작성자 ID를 반환하는 메서드 - 권한 체크용
    public Long getPostAuthorId(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("commentId가 존재하지 않습니다."));
        return comment.getUserId();
    }

    // ROLE_ADMIN 반환하는 메서드 - 권한 체크용
    public Collection<GrantedAuthority> getPostAuthorAuth() {
        Collection<GrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return role;
    }
}
