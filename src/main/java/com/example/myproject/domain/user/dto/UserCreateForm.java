package com.example.myproject.domain.user.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

    private String loginId;

    private String password;

    private String nickname;
}
