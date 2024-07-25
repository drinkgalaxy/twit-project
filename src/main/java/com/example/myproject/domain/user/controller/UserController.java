package com.example.myproject.domain.user.controller;

import com.example.myproject.domain.user.dto.UserCreateForm;
import com.example.myproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 회원가입
    @GetMapping("/users/registration")
    public String registration(UserCreateForm userCreateForm) {
        return "join";
    }
//
//    @PostMapping("/users/registration")
//    public String

    // 로그인


    // 로그아웃


}
