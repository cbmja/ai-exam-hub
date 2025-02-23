package com.aiexamhub.exam.controller;

import com.aiexamhub.exam.dto.ExtractHub;
import com.aiexamhub.exam.dto.Member;
import com.aiexamhub.exam.dto.Page;
import com.aiexamhub.exam.service.ExtractHubService;
import com.aiexamhub.exam.service.ExtractQuestionService;
import com.aiexamhub.exam.service.MemberService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ai-exam-hub")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ExtractHubService extractHubService;
    private final ExtractQuestionService extractQuestionService;


    @PostMapping("/login")
    @ResponseBody
    public String loginProc(@RequestBody Member form , HttpServletResponse response , Model model){

        Member member = memberService.EmailLogin(form , response , model);

        return member.getRes();
    }

    @GetMapping("/mypage/repository")
    public String mypage(ServletRequest servletRequest , Model model , @RequestParam(name = "page" ,defaultValue = "0") int page , @RequestParam(name = "search" ,defaultValue = "") String search){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        model.addAttribute("isLogin" , (Boolean)req.getAttribute("isLogin"));

        String memberCode = (String)req.getAttribute("memberCode");

        Page p = new Page();
        p.setSearch(search);
        p.setMemberCode(memberCode);
        int total = extractHubService.getTotal(p);

        Page form = new Page(page,total); //현재 페이지

        form.setMemberCode(memberCode);
        form.setSearch(search); // 검색어
        form.setSort("DESC"); // 정렬

        List<ExtractHub> list = extractHubService.getRepositories(form);

        for(ExtractHub eh : list){
            eh.setCount(extractQuestionService.CountByExtractHubCode(eh.getExtractHubCode()));
        }

        model.addAttribute("list" , list);
        model.addAttribute("page" , form);
        return "/view/mypage/repositories";
    }

}
