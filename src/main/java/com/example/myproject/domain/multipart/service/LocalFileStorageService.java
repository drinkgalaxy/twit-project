package com.example.myproject.domain.multipart.service;

import com.example.myproject.domain.multipart.entity.Multipart;
import com.example.myproject.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class LocalFileStorageService {

    private final PostRepository postRepository;

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    // 멀티파트 파일을 서버에 저장한다.
    public Multipart saveFile(MultipartFile file) throws IOException{
        if (file.isEmpty()) {
            return null;
        }
        String originalFilename = file.getOriginalFilename();
        String storedFilename = createStoreFileName(originalFilename);
        file.transferTo(new File(getFullPath(storedFilename)));
        return new Multipart(originalFilename, storedFilename);
    }

    // 서버 내부에서 관리하는 파일명은 유일한 UUID 이름을 생성해서 충돌하지 않도록 한다.
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    // 확장자를 별도로 추출해서 서버 내부에서 관리하는 파일명에 붙여준다.
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
