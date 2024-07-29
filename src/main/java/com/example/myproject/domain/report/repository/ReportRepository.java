package com.example.myproject.domain.report.repository;

import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("select r from Report r where r.use_yn = true")
    List<Report> findAllAble();

    @Query("select r from Report r where r.id = :reportId AND r.use_yn = true")
    Report findByIdAble(Long reportId);
}
