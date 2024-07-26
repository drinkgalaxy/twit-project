package com.example.myproject.domain.user.service;

import com.example.myproject.domain.user.dto.UserCreateRequest;
import com.example.myproject.domain.user.dto.UserResponse;
import com.example.myproject.domain.user.entity.User;
import com.example.myproject.domain.user.enumtype.Role;
import com.example.myproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    // 회원가입
    public User create(UserCreateRequest userCreateRequest) {
        User savedUser = User.builder()
                .loginId(userCreateRequest.getLoginId())
                .password(encoder.encode(userCreateRequest.getPassword()))
                .nickname(userCreateRequest.getNickname())
                .role(Role.MEMBER)
                .totalPostIt(0L)
                .availablePostIt(0L)
                .exchangedPostIt(0L)
                .use_yn(true)
                .is_verified(false)
                .build();
        log.info(savedUser.getNickname());
        return userRepository.save(savedUser);
    }

    // 아이디 중복 검사
    public boolean isLoginIdExists(String LoginId) {
        return userRepository.findByLoginId(LoginId).isPresent();
    }

    // 닉네임 중복 검사
    public boolean isNicknameExists(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    // 회원 조회
    public UserResponse findById(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("userId doesn't exist"));
        return findUser.toResponse();
    }


    // 닉네임 변경
    public UserResponse updateNickname(Long userId, String newNickname) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("userId doesn't exist"));
        findUser.updateNickname(newNickname);
        log.info("변경 후 닉네임 = "+ findUser.getNickname());
        return findUser.toResponse();
    }

    // 비밀번호 변경
    public UserResponse updatePassword(Long userId, String newPassword) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("userId doesn't exist"));
        findUser.updatePassword(encoder.encode(newPassword));
        return findUser.toResponse();
    }


    // 회원 삭제
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("userId doesn't exist"));
        userRepository.delete(user);
        user.toResponse();
    }
}
