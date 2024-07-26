package com.example.myproject.domain.user.controller;

import com.example.myproject.common.security.CustomUserDetails;
import com.example.myproject.domain.user.dto.*;
import com.example.myproject.domain.user.entity.User;
import com.example.myproject.domain.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder;


    // 회원가입
    @PostMapping("/users/registration")
    public ResponseEntity<?> registration(@RequestBody @Validated UserCreateRequest userCreateRequest) {
        if (userService.isLoginIdExists(userCreateRequest.getLoginId())) {
            return ResponseEntity.badRequest().body("이미 존재하는 아이디입니다. loginId = "+userCreateRequest.getLoginId());
        }
        if (userService.isNicknameExists(userCreateRequest.getNickname())) {
            return ResponseEntity.badRequest().body("이미 존재하는 닉네임입니다. nickname = "+ userCreateRequest.getNickname());
        }

        User user = userService.create(userCreateRequest);
        UserResponse userResponse = user.toResponse();

        return ResponseEntity.ok(userResponse);
    }

    // 로그인
    @PostMapping("/users/login")
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
                                   @RequestBody UserLoginRequest userLoginRequest) {
        try {
            // 사용자 인증
            UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginRequest.getLoginId());

            // 사용자 정보가 없을 때 처리
            if (userDetails == null) {
                return ResponseEntity.badRequest().body("존재하지 않는 아이디입니다. loginId = " + userLoginRequest.getLoginId());
            }

            // 비밀번호 비교 (암호화된 비밀번호와 입력된 비밀번호 비교)
            if (!encoder.matches(userLoginRequest.getPassword(), userDetails.getPassword())) {
                return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다. password = "+ userLoginRequest.getPassword());
            }

            // 인증 객체 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userLoginRequest.getPassword(), new ArrayList<>());

            // 인증 성공 시 SecurityContext 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute
                    (HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                            SecurityContextHolder.getContext());

            // 쿠키 설정
            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setDomain("localhost");
            cookie.setMaxAge(30000 * 60);
            response.addCookie(cookie);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuthenticationException e) {
            // 인증 실패
            return ResponseEntity.badRequest().body("로그인 인증에 실패했습니다.");
        }
    }

    // 로그아웃
    @PostMapping("/users/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }

        // 클라이언트에게 쿠키 삭제 요청
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    // 닉네임 변경
    @PatchMapping("/users/nickname/{userId}")
    public ResponseEntity<UserResponse> updateUserLoginId(@PathVariable Long userId,
                                                          @RequestBody UserUpdateNicknameRequest request) {
        UserResponse userResponse = userService.updateNickname(userId, request.getNickname());
        return ResponseEntity.ok(userResponse);
    }

    // 비밀번호 변경
    @PatchMapping("/users/password/{userId}")
    public ResponseEntity<UserResponse> updateUserPassword(@PathVariable Long userId,
                                                           @RequestBody @Validated UserUpdatePasswordRequest request) {
        UserResponse userResponse = userService.updatePassword(userId, request.getPassword());
        return ResponseEntity.ok(userResponse);
    }

    // 회원 조회 - 체크용
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> showUser(@PathVariable Long userId) {
        UserResponse userResponse = userService.findById(userId);
        return ResponseEntity.ok(userResponse);
    }

    // todo 이미지 업로드/삭제


    // 회원 탈퇴
    @DeleteMapping("/users/delete")
    public ResponseEntity<?> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                     HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }

        Long user_id = customUserDetails.getUserId();
        userService.delete(user_id);

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

}
