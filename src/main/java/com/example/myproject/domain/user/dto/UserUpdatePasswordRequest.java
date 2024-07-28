package com.example.myproject.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
public class UserUpdatePasswordRequest {

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 입력해주세요.")
    private String password;
}
