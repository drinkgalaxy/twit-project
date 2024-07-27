package com.example.myproject.domain.post.repository;

import com.example.myproject.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

        @Query("select p from Post p where p.id = :postId AND p.use_yn = true")
        Post findByIdAble(Long postId);

        @Query("select p from Post p where p.use_yn = true")
        Page<Post> findAll (Pageable pageable);

        @Modifying
        @Query("update Post p set p.use_yn = false where p.id = :postId")
        void disablePostById(Long postId);

}
