package com.example.myproject.domain.user.entity;

import com.example.myproject.common.base.BaseTimeEntity;
import com.example.myproject.domain.user.dto.UserResponse;
import com.example.myproject.domain.user.enumtype.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String loginId;

    private String password;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Long totalPostIt;

    private Long availablePostIt;

    private Long exchangedPostIt;

    private boolean use_yn;

    private boolean is_verified;

    @Builder
    public User(Long id, String loginId, String password, String nickname, String email, Role role, Long totalPostIt, Long availablePostIt, Long exchangedPostIt, boolean use_yn, boolean is_verified) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.totalPostIt = availablePostIt + exchangedPostIt;
        this.availablePostIt = availablePostIt;
        this.exchangedPostIt = exchangedPostIt;
        this.use_yn = use_yn;
        this.is_verified = is_verified;
    }

    // User 객체를 받아서 UserResponse 를 생성
    public UserResponse toResponse() {
        return UserResponse
                .builder()
                .id(id)
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .role(role)
                .totalPostIt(totalPostIt)
                .availablePostIt(availablePostIt)
                .exchangedPostIt(exchangedPostIt)
                .use_yn(use_yn)
                .is_verified(is_verified)
                .build();
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void PostItCountUpdate(String update) {
        if (update.equals("postUpload")) {
            if (this.availablePostIt - 10 < 0) {
                this.availablePostIt = 0L;
            } else {
                this.availablePostIt -= 10;
            }
        } else if (update.equals("commentUpload")) {
            this.availablePostIt += 1;
        }
    }
}
