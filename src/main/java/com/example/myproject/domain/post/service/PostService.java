package com.example.myproject.domain.post.service;

import com.example.myproject.domain.comment.entity.Comment;
import com.example.myproject.domain.comment.repository.CommentRepository;
import com.example.myproject.domain.multipart.entity.Multipart;
import com.example.myproject.domain.multipart.repository.MultipartRepository;
import com.example.myproject.domain.multipart.service.LocalFileStorageService;
import com.example.myproject.domain.post.dto.PostCreateRequest;
import com.example.myproject.domain.post.dto.PostResponse;
import com.example.myproject.domain.post.dto.PostUpdateRequest;
import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.post.repository.PostRepository;
import com.example.myproject.domain.scrap.entity.Scrap;
import com.example.myproject.domain.scrap.repository.ScrapRepository;
import com.example.myproject.domain.user.entity.User;
import com.example.myproject.domain.user.repository.UserRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final LocalFileStorageService localFileStorageService;
    private final MultipartRepository multipartRepository;

    public Post save(PostCreateRequest request, Long userId, MultipartFile file) throws IOException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found type id"));

        // 이미지 업로드
        Multipart multipart = localFileStorageService.saveFile(file);
        multipartRepository.save(multipart);

        log.info("글 업로드 전 포스트잇 개수 = "+ user.getAvailablePostIt());
        if (user.getAvailablePostIt() < 10) {
            throw new IllegalArgumentException("포스트잇의 개수가 부족합니다. 현재 포스트잇 개수 = "+ user.getAvailablePostIt() + "\n"
                    + "현재 부족한 포스트잇 개수 = "+ (10 - user.getAvailablePostIt()));
        }
        user.PostItCountUpdate("postUpload");
        log.info("글 업로드 후 포스트잇 개수 = "+ user.getAvailablePostIt());
        user.exchangedPostItCountUpdate();

        // postRepository 에 filename 도 같이 저장
        return postRepository.save(request.toEntity(user.getId(), user.getNickname(), multipart.getOriginalFileName()));
    }

    public PostResponse findOnePost(Long postId) {
        Post post = postRepository.findByIdAble(postId);
        post.viewCountUpdate();
        return post.toResponse();
    }

    public Page<Post> findPosts(int page) {
        // 생성 순으로 내림차순 정렬
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 3);
        Page<Post> findPosts = postRepository.findAll(pageable);

        if (findPosts.isEmpty()) {
            throw new IllegalArgumentException("등록된 게시글이 없습니다.");
        }
        return findPosts;
    }

    // 본인이 올린 게시글 목록 조회
    public List<PostResponse> findMyPosts(Long userId) {
        List<Post> posts = postRepository.findAllByUserId(userId);
        return posts.stream()
                .map(Post::toResponse)
                .toList();
    }

    // 본인이 댓글 단 게시글 목록 조회
    public List<PostResponse> findMyComments(Long userId) {
        List<Comment> comments = commentRepository.findAllByUserId(userId);

        List<Post> list = new ArrayList<>();
        for (Comment comment : comments) {
            Long postId = comment.getPostId();
            list.add(postRepository.findByIdAble(postId));
        }

        return list.stream()
                .map(Post::toResponse)
                .toList();
    }

    // 본인이 스크랩 한 게시글 목록 조회
    public List<PostResponse> findScraps(Long userId) {
        List<Scrap> scraps = scrapRepository.findAllByUserId(userId);

        List<Post> list = new ArrayList<>();
        for (Scrap scrap : scraps) {
            Long postId = scrap.getPostId();
            list.add(postRepository.findByIdAble(postId));
        }

        return list.stream()
                .map(Post::toResponse)
                .toList();
    }


    public Post update(Long postId, PostUpdateRequest request, MultipartFile file) throws IOException {
        // 이미지 업로드
        Multipart multipart = localFileStorageService.saveFile(file);
        multipartRepository.save(multipart);

        Post post = postRepository.findByIdAble(postId);

        // postRepository 에 filename 도 같이 업데이트
        post.update(request.getContents(), request.getDescription(), multipart.getOriginalFileName());
        return post;
    }

    public void disablePostById(Long postId) {
        postRepository.disablePostById(postId);
    }

    // 특정 게시글의 작성자 ID를 반환하는 메서드 - 권한 체크용
    public Long getPostAuthorId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("postId가 존재하지 않습니다."));
        return post.getUserId();
    }

    // ROLE_ADMIN 반환하는 메서드 - 권한 체크용
    public Collection<GrantedAuthority> getPostAuthorAuth() {
        Collection<GrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return role;
    }
}
