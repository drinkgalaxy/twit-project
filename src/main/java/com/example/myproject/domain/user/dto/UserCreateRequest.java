package com.example.myproject.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserCreateRequest {

    private String loginId;

    private String password;

    private String nickname;
}
