package com.example.myproject.domain.user.dto;

import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
public class UserUpdatePasswordRequest {

    private String password;
}
