package com.example.myproject.domain.multipart.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    // 파일 업로드
    String uploadFile(MultipartFile file);

    // 파일 다운로드
    byte[] downloadFile(String fileKey);

}
