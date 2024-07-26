package com.example.myproject;

import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.post.repository.PostRepository;
import com.example.myproject.domain.user.entity.User;
import com.example.myproject.domain.user.enumtype.Role;
import com.example.myproject.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder encoder;

    // 테스트용 데이터 추가

    // user
    @PostConstruct
    public void Init() {
        User savedUser = User.builder()
                .loginId("testId")
                .password(encoder.encode("1234"))
                .nickname("테스트 닉네임")
                .role(Role.MEMBER)
                .totalPostIt(0L)
                .availablePostIt(0L)
                .exchangedPostIt(0L)
                .use_yn(true)
                .is_verified(false)
                .build();
        userRepository.save(savedUser);

        Post savedPost1 = Post.builder()
                .userId(savedUser.getId())
                .title("제목")
                .contents("내용")
                .description("한줄 소개")
                .viewCount(0L)
                .use_yn(true)
                .createdBy(savedUser.getNickname())
                .lastModifiedBy(savedUser.getNickname())
                .build();

        Post savedPost2 = Post.builder()
                .userId(savedUser.getId())
                .title("제목222")
                .contents("내용222")
                .description("한줄 소개222")
                .viewCount(0L)
                .use_yn(true)
                .createdBy(savedUser.getNickname())
                .lastModifiedBy(savedUser.getNickname())
                .build();

        postRepository.save(savedPost1);
        postRepository.save(savedPost2);
    }

}


