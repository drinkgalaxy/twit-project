package com.example.myproject.common.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // authorizeHttpRequests : 특정한 경로에다가 요청을 허용하거나 거부 설정
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers( "/**").permitAll()
                .requestMatchers("/my-page", "upload-post").hasAnyRole("ADMIN", "MEMBER")
                .requestMatchers("/admin-page").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

//        http
//                .httpBasic(Customizer.withDefaults());

// HttpBasic 로그인 구현을 위해 잠시 주석처리
//        http.formLogin((auth) -> auth.loginPage("/login")
//                .loginProcessingUrl("/loginProc").permitAll()
//        );
//
        http.csrf((auth) -> auth.disable()
        );
//
//        http
//                .sessionManagement((auth) -> auth
//                        .maximumSessions(1)
//                        .maxSessionsPreventsLogin(true)
//                );
//
//        http
//                .sessionManagement((auth) -> auth
//                        .sessionFixation().changeSessionId()
//                );
//
//        http
//                .logout((auth) -> auth.logoutUrl("/logout")
//                        .logoutSuccessUrl("/")
//                );

        return http.build();
    }
}
