package com.example.myproject.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
public class UserUpdateNicknameRequest {

    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;
}
