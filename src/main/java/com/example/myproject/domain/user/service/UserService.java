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

@RequiredArgsConstructor
@Service
@Slf4j
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

    // 아이디 존재 확인
    public UserResponse findByLoginId(String LoginId) {
        User user = userRepository.findByLoginId(LoginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다. loginId = "+ LoginId));
        return user.toResponse();
    }

    // 닉네임 중복 검사
    public boolean isNicknameExists(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    // 회원 조회
    public List<User> findAllAble() {
        return userRepository.findAllAble();
    }

    // 회원 삭제
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("member_id doesn't exist"));
        userRepository.delete(user);
        user.toResponse();
    }
}
