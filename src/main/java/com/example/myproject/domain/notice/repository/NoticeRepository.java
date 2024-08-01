package com.example.myproject.domain.notice.repository;

import com.example.myproject.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("select n from Notice n")
    Notice findNotice();

}
