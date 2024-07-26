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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return postRepository.save(request.toEntity(user.getId(), user.getNickname()));
    }

    public PostResponse findOnePost(Long postId) {
        Post post = postRepository.findByIdAble(postId);
        return post.toResponse();
    }

    public Post update(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findByIdAble(postId);
        post.update(request.getContents(), request.getDescription());
        return post;
    }

    public void disablePostById(Long postId) {
        postRepository.disablePostById(postId);
    }
}
