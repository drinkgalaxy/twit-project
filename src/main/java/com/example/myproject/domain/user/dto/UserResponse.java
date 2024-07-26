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

    // User 객체를 받아서 UserResponse 를 생성하는 생성자
    public UserResponse(User user) {
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.totalPostIt = user.getTotalPostIt();
        this.availablePostIt = user.getAvailablePostIt();
        this.exchangedPostIt = user.getExchangedPostIt();
        this.use_yn = user.isUse_yn();
        this.is_verified = user.is_verified();
    }


}
