package com.example.myproject.domain.post.entity;

import com.example.myproject.common.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private Long userId;

    private String title;

    private String contents;

    private String description;

    private Long viewCount;

    private boolean use_yn;

    private String createdBy;

    private String lastModifiedBy;
}
