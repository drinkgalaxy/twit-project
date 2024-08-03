package com.example.myproject.domain.multipart.controller;

import com.example.myproject.domain.multipart.entity.Multipart;
import com.example.myproject.domain.multipart.repository.MultipartRepository;
import com.example.myproject.domain.multipart.service.LocalFileStorageService;
import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MultipartController {

    private final LocalFileStorageService localFileStorageService;
    private final MultipartRepository multipartRepository;

    // 파일 다운로드
    @GetMapping("/file/download/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String filename) throws MalformedURLException {
        Multipart multipart = multipartRepository.findByFilename(filename);
        String storedFilename = multipart.getStoredFileName();
        String originalFileName = multipart.getOriginalFileName();

        UrlResource resource = new UrlResource("file:" +
                localFileStorageService.getFullPath(storedFilename));

        log.info("uploadFilename = {}", originalFileName);
        String encodedUploadFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
