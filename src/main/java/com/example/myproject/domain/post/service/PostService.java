package com.example.myproject.domain.post.service;

import com.example.myproject.domain.post.dto.PostCreateRequest;
import com.example.myproject.domain.post.dto.PostResponse;
import com.example.myproject.domain.post.dto.PostUpdateRequest;
import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.post.repository.PostRepository;
import com.example.myproject.domain.user.entity.User;
import com.example.myproject.domain.user.repository.UserRepository;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post save(PostCreateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found type id"));
        log.info("글 업로드 전 포스트잇 개수 = "+ user.getAvailablePostIt());
        if (user.getAvailablePostIt() < 10) {
            throw new IllegalArgumentException("포스트잇의 개수가 부족합니다. 현재 포스트잇 개수 = "+ user.getAvailablePostIt() + "\n"
                    + "현재 부족한 포스트잇 개수 = "+ (10 - user.getAvailablePostIt()));
        }
        user.PostItCountUpdate("postUpload");
        log.info("글 업로드 후 포스트잇 개수 = "+ user.getAvailablePostIt());
        return postRepository.save(request.toEntity(user.getId(), user.getNickname()));
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


    public Post update(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findByIdAble(postId);

        post.update(request.getContents(), request.getDescription());
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
