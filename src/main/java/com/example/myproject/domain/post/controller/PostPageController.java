package com.example.myproject.domain.post.controller;

import com.example.myproject.common.security.CustomUserDetails;
import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequiredArgsConstructor
@Slf4j
public class PostPageController {

    private final PostService postService;

    @GetMapping("/main")
    public String mainPage(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails,
                           @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Post> paging = postService.findPosts(page);
        int totalPages = paging.getTotalPages();
        int currentPageGroup = page / 5; // 한 그룹당 5개의 페이지 번호
        int startPage = currentPageGroup * 5;
        int endPage = Math.min((currentPageGroup + 1) * 5 - 1, totalPages - 1);

        model.addAttribute("postList", paging.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPageGroup", currentPageGroup);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "main";
    }
}
