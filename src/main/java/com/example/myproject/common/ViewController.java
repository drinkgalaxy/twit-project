package com.example.myproject.common;

import com.example.myproject.common.security.CustomUserDetails;
import com.example.myproject.domain.comment.entity.Comment;
import com.example.myproject.domain.comment.service.CommentService;
import com.example.myproject.domain.notice.dto.NoticeResponse;
import com.example.myproject.domain.notice.service.NoticeService;
import com.example.myproject.domain.post.dto.PostResponse;
import com.example.myproject.domain.post.entity.Post;
import com.example.myproject.domain.post.service.PostService;
import com.example.myproject.domain.report.dto.ReportResponse;
import com.example.myproject.domain.report.service.ReportService;
import com.example.myproject.domain.scrap.service.ScrapService;
import com.example.myproject.domain.user.dto.UserResponse;
import com.example.myproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ViewController {

    private final CommentService commentService;
    private final PostService postService;
    private final NoticeService noticeService;
    private final UserService userService;
    private final ScrapService scrapService;
    private final ReportService reportService;

    // 인트로 페이지
    @GetMapping("/intro")
    public String introPage() {
        return "intro";
    }

    // 회원가입 페이지
    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 메인 페이지
    @GetMapping("/")
    public String mainPage(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails,
                           @RequestParam(value = "page", defaultValue = "0") int page) {

        // 로그인 여부 확인
        boolean isLoggedIn = (customUserDetails != null);
        model.addAttribute("isLoggedIn", isLoggedIn);

        // 공지 조회
        NoticeResponse notice = noticeService.findNotice();
        model.addAttribute("notice", notice);

        // ROLE_ADMIN 권한 확인
        boolean isAdmin = isLoggedIn && customUserDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        // 페이징
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

    // 게시글 상세 페이지
    @GetMapping("/posts/{postId}")
    public String mainDetails(@PathVariable Long postId, Model model,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        
        // 로그인 여부 확인
        boolean isLoggedIn = (customUserDetails != null);
        model.addAttribute("isLoggedIn", isLoggedIn);

        // id를 사용하여 게시글 정보를 조회
        PostResponse post = postService.findOnePost(postId);
        model.addAttribute("post", post);

        // id를 사용하여 댓글 정보를 조회
        List<Comment> commentList = commentService.findComments(postId);
        model.addAttribute("commentList", commentList);

        // 스크랩 상태 조회
        boolean isScrapped = false;
        if (customUserDetails != null) {
            Long userId = customUserDetails.getUserId();
            isScrapped = scrapService.hasScrap(postId, userId);
        }
        model.addAttribute("isScrapped", isScrapped);
        
        return "main-details"; // 상세 페이지의 뷰 이름

    }

    // 글 업로드 페이지
    @GetMapping("/upload-post")
    public String uploadPost(Model model,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // 로그인 여부 확인
        boolean isLoggedIn = (customUserDetails != null);
        model.addAttribute("isLoggedIn", isLoggedIn);

        return "upload-post";
    }

    // 내 정보 페이지
    @GetMapping("/my-page")
    public String myPage(Model model,
                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        // 로그인 여부 확인
        boolean isLoggedIn = (customUserDetails != null);
        model.addAttribute("isLoggedIn", isLoggedIn);

        // 관리자 여부 확인
        boolean isAdmin = (customUserDetails != null && customUserDetails.getAuthorities().containsAll(postService.getPostAuthorAuth()));
        log.info("어드민 확인용 = "+ isAdmin);
        model.addAttribute("isAdmin", isAdmin);

        Long userId = 0L;
        if (isLoggedIn) {
            userId = customUserDetails.getUserId();
        }
        UserResponse userResponse = userService.findById(userId);
        model.addAttribute("user", userResponse);

        // 올린 글 조회
        List<PostResponse> myPostList = postService.findMyPosts(userId);
        model.addAttribute("myPostList", myPostList);

        // 피드백한 글 조회
        List<PostResponse> myCommentList = postService.findMyComments(userId);
        model.addAttribute("myCommentList", myCommentList);

        // 스크랩한 글 조회
        List<PostResponse> myScrapList = postService.findScraps(userId);
        model.addAttribute("myScrapList", myScrapList);

        return "my-page";
    }

    // 관리자 페이지
    @GetMapping("/admin-page")
    public String adminPage(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        // 로그인 여부 확인
        boolean isLoggedIn = (customUserDetails != null);
        model.addAttribute("isLoggedIn", isLoggedIn);

        // 관리자 여부 확인
        boolean isAdmin = (customUserDetails != null && customUserDetails.getAuthorities().containsAll(postService.getPostAuthorAuth()));
        log.info("어드민 확인용 = "+ isAdmin);
        model.addAttribute("isAdmin", isAdmin);

        // 신고 목록 조회
        List<ReportResponse> responses = reportService.findReports();
        model.addAttribute("reportList", responses);

        return "admin-page";
    }
}
