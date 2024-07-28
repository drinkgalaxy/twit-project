package com.example.myproject.domain.comment.repository;

import com.example.myproject.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
