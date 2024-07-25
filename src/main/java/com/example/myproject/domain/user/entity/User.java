package com.example.myproject.domain.user.entity;

import com.example.myproject.common.base.BaseTimeEntity;
import com.example.myproject.domain.user.enumtype.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 30, unique = true)
    private String loginId;

    private String password;

    @Column(length = 30, unique = true)
    private String nickname;


    @Column(unique = true)
    private String email;

    private Long totalPostIt;

    private Long availablePostIt;

    private Long exchangedPostIt;


    private boolean use_yn;

    private boolean is_verified;
}
