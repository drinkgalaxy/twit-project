package com.example.myproject.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserCreateRequest {

    private String loginId;

    private String password;

    private String nickname;
}
