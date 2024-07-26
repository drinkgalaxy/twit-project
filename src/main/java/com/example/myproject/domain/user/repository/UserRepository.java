package com.example.myproject.domain.user.repository;

import com.example.myproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByNickname(String nickname);

    @Query("select u from User u where u.use_yn = true")
    List<User> findAllAble();


}
