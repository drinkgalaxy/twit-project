package com.example.myproject.domain.comment.repository;

import com.example.myproject.domain.comment.dto.CommentResponse;
import com.example.myproject.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where  c.postId = :postId")
    List<Comment> findAllById(Long postId);

    @Query("select c from Comment c where c.id = :commentId AND c.use_yn = true")
    Comment findByIdAble(Long commentId);

    @Modifying
    @Query("update Comment c set c.use_yn = false where c.id = :commentId")
    void disableCommentById(Long commentId);
}
