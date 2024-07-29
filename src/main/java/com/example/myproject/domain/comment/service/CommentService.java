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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Comment save(Long userId, CommentCreateRequest request, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found type id"));

        log.info("댓글 업로드 전 포스트잇 개수 = "+ user.getAvailablePostIt());
        user.PostItCountUpdate("commentUpload");
        log.info("댓글 업로드 후 포스트잇 개수 = "+ user.getAvailablePostIt());

        return commentRepository.save(request.toEntity(user.getId(), postId));
    }

    public List<Comment> findComments(Long postId) {
        return commentRepository.findAllById(postId);
    }

    public void deleteComment(Long userId, Long commentId) {
        Comment findComment = commentRepository.findByIdAble(commentId);

        validaUser(findComment, userId, "본인이 작성한 포스트잇만 삭제할 수 있습니다.");

        commentRepository.disableCommentById(findComment.getId());
    }

    private void validaUser(Comment findComment, Long userId, String s) {
        if (!findComment.getUserId().equals(userId)) {
            throw new IllegalArgumentException(s);
        }
    }
}
