package com.example.myproject.domain.multiparts.entity;

import com.example.myproject.common.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Multipart extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "multipart_id")
    private Long id;

    private Long postId;

    private Long userId;

    private String originalFileName;

    private String storedFileName;

    private Long size;

    private String url;

    private boolean use_yn;

}
