package com.example.myproject.common;

import com.example.myproject.domain.user.entity.User;
import com.example.myproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommonController {

    private final UserService userService;

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/intro")
    public String introPage() {
        return "intro";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("user", new User());
        return "join";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/my-page")
    public String myPage(Model model) {
        model.addAttribute("user");
        return "my-page";
    }
}
