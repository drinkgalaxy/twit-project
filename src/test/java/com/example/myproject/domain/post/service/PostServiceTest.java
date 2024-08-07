package com.example.myproject.domain.post.service;

import com.example.myproject.domain.comment.entity.Comment;
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
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        Post savedPost = Post.builder()
                .id(1L)
                .userId(savedUser.getId())
                .title("제목")
                .description("한줄소개")
                .contents("내용")
                .fileName("파일 이름")
                .build();

        when(userRepository.findById(savedUser.getId())).thenReturn(Optional.of(savedUser));
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // when
        Post resultPost = postService.save(request, savedUser.getId(), savedPost.getFileName());

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
        Long postId = 1L;
        Post post = mock(Post.class);
        PostResponse response = PostResponse.builder()
                .id(1L)
                .userId(1L)
                .title("제목")
                .contents("내용")
                .description("한줄소개")
                .viewCount(1L)
                .use_yn(true)
                .createdBy("닉네임")
                .lastModifiedBy("닉네임")
                .fileName("파일 이름")
                .build();

        when(postRepository.findByIdAble(postId)).thenReturn(post);
        when(post.toResponse()).thenReturn(response);

        // when
        PostResponse result = postService.findOnePost(postId);

        // then
        assertEquals(response, result);
        verify(post).viewCountUpdate();
    }

    @Test
    @DisplayName("본인이 올린 게시글 목록 조회")
    void findMyPosts() {
        // given
        Long userId = 1L;

        Post post1 = mock(Post.class);
        PostResponse response1 = PostResponse.builder()
                .id(1L)
                .userId(userId)
                .title("제목1")
                .contents("내용1")
                .description("한줄소개1")
                .viewCount(1L)
                .use_yn(true)
                .createdBy("닉네임1")
                .lastModifiedBy("닉네임1")
                .fileName("파일 이름1")
                .build();

        Post post2 = mock(Post.class);
        PostResponse response2 = PostResponse.builder()
                .id(2L)
                .userId(userId)
                .title("제목2")
                .contents("내용2")
                .description("한줄소개2")
                .viewCount(1L)
                .use_yn(true)
                .createdBy("닉네임2")
                .lastModifiedBy("닉네임2")
                .fileName("파일 이름2")
                .build();
        when(post1.toResponse()).thenReturn(response1);
        when(post2.toResponse()).thenReturn(response2);
        when(postRepository.findAllByUserId(userId)).thenReturn(Arrays.asList(post1, post2));

        // when
        List<PostResponse> result = postService.findMyPosts(userId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("본인이 댓글 단 게시글 목록 조회")
    void findMyComments() {
        // given
        Long userId = 1L;
        Long commentId = 1L;

        // 댓글 목록
        Comment comment1 = mock(Comment.class);
        Comment comment2 = mock(Comment.class);

        // 댓글이 달린 게시글
        Long postId1 = 1L;
        Long postId2 = 2L;
        Post post1 = mock(Post.class);
        Post post2 = mock(Post.class);

        PostResponse response1 = PostResponse.builder()
                .id(postId1)
                .userId(userId)
                .title("제목")
                .contents("내용")
                .description("한줄소개")
                .viewCount(1L)
                .use_yn(true)
                .createdBy("닉네임")
                .lastModifiedBy("닉네임")
                .fileName("파일 이름")
                .build();

        PostResponse response2 = PostResponse.builder()
                .id(postId2)
                .userId(userId)
                .title("제목")
                .contents("내용")
                .description("한줄소개")
                .viewCount(1L)
                .use_yn(true)
                .createdBy("닉네임")
                .lastModifiedBy("닉네임")
                .fileName("파일 이름")
                .build();

        // Mock 설정
        when(comment1.getPostId()).thenReturn(postId1);
        when(comment2.getPostId()).thenReturn(postId2);
        when(commentRepository.findAllByUserId(userId)).thenReturn(Arrays.asList(comment1, comment2));
        when(postRepository.findByIdAble(postId1)).thenReturn(post1);
        when(postRepository.findByIdAble(postId2)).thenReturn(post2);
        when(post1.toResponse()).thenReturn(response1);
        when(post2.toResponse()).thenReturn(response2);

        // when
        List<PostResponse> result = postService.findMyComments(userId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
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