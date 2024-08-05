package com.example.myproject.domain.multipart.entity;

import com.example.myproject.common.base.BaseTimeEntity;
import com.example.myproject.domain.multipart.dto.MultipartResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Multipart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "multipart_id")
    private Long id;

    private Long postId;

    private String originalFileName;

    private String storedFileName;


    @Builder
    public Multipart(Long postId, String originalFilename, String storedFilename) {
        this.postId = postId;
        this.originalFileName = originalFilename;
        this.storedFileName = storedFilename;
    }


    public MultipartResponse toResponse() {
        return MultipartResponse.builder()
                .id(id)
                .postId(postId)
                .originalFileName(originalFileName)
                .storedFileName(storedFileName)
                .build();
    }

}
