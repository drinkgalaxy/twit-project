package com.example.myproject.domain.user.repository;

import com.example.myproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    // userId 중복 검증


}
