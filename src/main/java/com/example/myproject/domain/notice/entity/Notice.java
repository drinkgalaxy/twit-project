package com.example.myproject.domain.notice.entity;

import com.example.myproject.common.base.BaseTimeEntity;
import com.example.myproject.domain.notice.dto.NoticeResponse;
import com.example.myproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private Long userId;

    private String noticeContents;

    private String lastModifiedBy;

    @Builder
    public Notice(Long id, Long userId, String noticeContents, String lastModifiedBy) {
        this.id = id;
        this.userId = userId;
        this.noticeContents = noticeContents;
        this.lastModifiedBy = lastModifiedBy;
    }

    public NoticeResponse toResponse() {
        return NoticeResponse.builder()
                .id(id)
                .userId(userId)
                .noticeContents(noticeContents)
                .lastModifiedBy(lastModifiedBy)
                .lastModifiedDate(getLastModifiedDate())
                .build();
    }

    public void update(String noticeContents, Long userId, String lastModifiedBy) {
        this.noticeContents = noticeContents;
        this.userId = userId;
        this.lastModifiedBy = lastModifiedBy;
    }
}
