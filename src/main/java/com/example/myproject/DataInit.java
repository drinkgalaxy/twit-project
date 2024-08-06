package com.example.myproject;

import com.example.myproject.domain.comment.entity.Comment;
import com.example.myproject.domain.comment.repository.CommentRepository;
import com.example.myproject.domain.notice.entity.Notice;
import com.example.myproject.domain.notice.repository.NoticeRepository;
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
    private final CommentRepository commentRepository;
    private final NoticeRepository noticeRepository;

    // 공지 추가
    @PostConstruct
    public void CreateNotice() {
        Notice notice = Notice.builder()
                .userId(0L)
                .noticeContents("오늘의 공지입니다.")
                .build();

        noticeRepository.save(notice);
    }

    // 테스트용 데이터 추가

    // user
    @PostConstruct
    public void Init() {
        // 유저 데이터 추가
        User savedUser1 = User.builder()
                .loginId("testId")
                .password(encoder.encode("1234"))
                .nickname("테스트 닉네임")
                .role(Role.MEMBER)
                .totalPostIt(10L)
                .availablePostIt(10L)
                .exchangedPostIt(0L)
                .use_yn(true)
                .is_verified(false)
                .build();
        userRepository.save(savedUser1);

        // 게시글 데이터 추가
        User savedUser2 = User.builder()
                .loginId("myId")
                .password(encoder.encode("1111"))
                .nickname("진진")
                .role(Role.MEMBER)
                .totalPostIt(0L)
                .availablePostIt(0L)
                .exchangedPostIt(0L)
                .use_yn(true)
                .is_verified(false)
                .build();
        userRepository.save(savedUser2);

        // 어드민
        User savedUser3 = User.builder()
                .loginId("adminId")
                .password(encoder.encode("1111"))
                .nickname("관리자")
                .role(Role.ADMIN)
                .totalPostIt(10000000L)
                .availablePostIt(10000000L)
                .exchangedPostIt(0L)
                .use_yn(true)
                .is_verified(false)
                .build();
        userRepository.save(savedUser3);

        Post savedPost1 = Post.builder()
                .userId(savedUser1.getId())
                .title("제목")
                .contents("내용")
                .description("한줄 소개")
                .viewCount(0L)
                .use_yn(true)
                .createdBy(savedUser1.getNickname())
                .lastModifiedBy(savedUser1.getNickname())
                .build();

        Post savedPost2 = Post.builder()
                .userId(savedUser1.getId())
                .title("제목222")
                .contents("내용222")
                .description("한줄 소개222")
                .viewCount(0L)
                .use_yn(true)
                .createdBy(savedUser1.getNickname())
                .lastModifiedBy(savedUser1.getNickname())
                .build();

        Post savedPost3 = Post.builder()
                .userId(savedUser2.getId())
                .title("제목333")
                .contents("내용333")
                .description("한줄 소개333")
                .viewCount(0L)
                .use_yn(true)
                .createdBy(savedUser2.getNickname())
                .lastModifiedBy(savedUser2.getNickname())
                .build();

        postRepository.save(savedPost1);
        postRepository.save(savedPost2);
        postRepository.save(savedPost3);

        // 댓글 데이터 추가
        Comment savedComment1 = Comment.builder()
                .userId(savedUser2.getId())
                .postId(savedPost1.getId())
                .comments("와 db 설계 진짜 잘하셨네요. 한 눈에 딱 들어와요. 테이블 이름 설정이나 변수 이름 설정도 좋네요.")
                .createdBy(savedUser2.getNickname())
                .use_yn(true)
                .build();

        commentRepository.save(savedComment1);

        Comment savedComment2 = Comment.builder()
                .userId(savedUser1.getId())
                .postId(savedPost3.getId())
                .comments("화면 디자인이 엄청 예쁘네요. 근데 로그인 화면에서 회원가입으로 넘어가는 버튼이 좀 더 컸으면 좋겠어요.")
                .createdBy(savedUser1.getNickname())
                .use_yn(true)
                .build();

        commentRepository.save(savedComment2);


    }

}


