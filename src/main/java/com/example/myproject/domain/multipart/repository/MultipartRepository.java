package com.example.myproject.domain.multipart.repository;

import com.example.myproject.domain.multipart.entity.Multipart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MultipartRepository extends JpaRepository<Multipart, Long> {

    @Query("select m from Multipart m where m.originalFileName = :filename AND m.postId = :postId")
    Multipart findByOriginalFileNameAndPostId(String filename, Long postId);

}
