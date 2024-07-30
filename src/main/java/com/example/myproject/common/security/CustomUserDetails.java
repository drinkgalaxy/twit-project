package com.example.myproject.common.security;

import com.example.myproject.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // 특정 권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        return auth;
    }

    public Long getUserId() {
        return user.getId();
    }

    // 로그인 아이디로 대신
    @Override
    public String getUsername() {
        return user.getLoginId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public String getNickname() {
        return user.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화
    @Override
    public boolean isEnabled() {
        return user.isUse_yn();
    }
}
