package com.example.myproject.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
public class UserUpdateNicknameRequest {

    @Size(min = 1, max = 10, message = "닉네임은 1글자 이상, 10글자 이내로 입력해주세요.")
    private String nickname;
}
