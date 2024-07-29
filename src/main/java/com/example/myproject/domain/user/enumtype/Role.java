package com.example.myproject.domain.user.enumtype;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }
}
