package com.example.myproject.domain.user.service;

import com.example.myproject.domain.user.entity.User;
import com.example.myproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String loginId, String password, String nickname) {
        User user = new User();
        user.setLoginId(loginId);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);
        userRepository.save(user);
        return user;
    }
}
