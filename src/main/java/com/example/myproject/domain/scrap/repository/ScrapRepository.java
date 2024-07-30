package com.example.myproject.domain.scrap.repository;

import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.scrap.entity.Scrap;
import com.example.myproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    @Query("select s from Scrap s where s.postId = :postId AND s.userId = :userId")
    Optional<Object> findByPostIdAndUserId(Long postId, Long userId);

    @Query("select s from Scrap s where s.userId = :userId")
    List<Scrap> findAllByUserId(Long userId);

}
