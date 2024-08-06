package com.example.myproject.domain.post.service;

import com.example.myproject.domain.comment.repository.CommentRepository;
import com.example.myproject.domain.multipart.repository.MultipartRepository;
import com.example.myproject.domain.multipart.service.LocalFileStorageService;
import com.example.myproject.domain.post.dto.PostCreateRequest;
import com.example.myproject.domain.post.dto.PostResponse;
import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.post.repository.PostRepository;
import com.example.myproject.domain.scrap.repository.ScrapRepository;
import com.example.myproject.domain.user.entity.User;
import com.example.myproject.domain.user.enumtype.Role;
import com.example.myproject.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ScrapRepository scrapRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LocalFileStorageService localFileStorageService;
    @Mock
    private MultipartRepository multipartRepository;

    @BeforeEach
    void setUp() {
        // Mockito 를 사용한 모킹 객체 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("게시글 저장 테스트_포스트잇 개수 10개 이상_성공")
    void save_success() throws IOException {
        // given
        User savedUser = User.builder()
                .id(1L)
                .loginId("아이디")
                .password("1234")
                .nickname("닉네임")
                .role(Role.MEMBER)
                .totalPostIt(10L)
                .availablePostIt(10L)
                .exchangedPostIt(0L)
                .use_yn(true)
                .is_verified(false)
                .build();

        PostCreateRequest request = PostCreateRequest.builder()
                .title("제목")
                .description("한줄소개")
                .contents("내용")
                .build();
        String fileName = "파일 이름";

        Post savedPost = Post.builder()
                .id(1L)
                .userId(savedUser.getId())
                .title("제목")
                .description("한줄소개")
                .contents("내용")
                .fileName(fileName)
                .build();

        when(userRepository.findById(savedUser.getId())).thenReturn(Optional.of(savedUser));
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // when
        Post resultPost = postService.save(request, savedUser.getId(), fileName);

        // then
        assertNotNull(resultPost);
        assertEquals(savedUser.getId(), resultPost.getUserId());
    }

    @Test
    @DisplayName("게시글 저장 테스트_포스트잇 개수 10개 미만_실패")
    void save_fail() throws IOException {
        // given
        User savedUser = User.builder()
                .id(1L)
                .loginId("아이디")
                .password("1234")
                .nickname("닉네임")
                .role(Role.MEMBER)
                .totalPostIt(0L)
                .availablePostIt(0L)
                .exchangedPostIt(0L)
                .use_yn(true)
                .is_verified(false)
                .build();
        when(userRepository.findById(savedUser.getId())).thenReturn(Optional.of(savedUser));

        PostCreateRequest request = PostCreateRequest.builder()
                .title("제목")
                .description("한줄소개")
                .contents("내용")
                .build();
        String fileName = "파일 이름";

        // then
        assertThrows(IllegalArgumentException.class, () -> postService.save(request, savedUser.getId(), fileName));

    }

    @Test
    @DisplayName("글 1개 가져오기")
    void findOnePost() throws IOException {
        // given

        // when


        // then

    }

    @Test
    @DisplayName("글 여러 개 가져오기_페이징")
    void findPosts() throws IOException {
        // given

    }

    @Test
    void findMyPosts() {
    }

    @Test
    void findMyComments() {
    }

    @Test
    void findScraps() {
    }

    @Test
    void update() {
    }

    @Test
    void disablePostById() {
    }

    @Test
    void getPostAuthorId() {
    }

    @Test
    void getPostAuthorAuth() {
    }
}