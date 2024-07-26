package com.example.myproject.domain.user.dto;

import com.example.myproject.domain.user.entity.User;
import com.example.myproject.domain.user.enumtype.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String loginId;

    private String password;

    private String nickname;

    private String email;

    private Role role;

    private Long totalPostIt;

    private Long availablePostIt;

    private Long exchangedPostIt;

    private boolean use_yn;

    private boolean is_verified;

}
