package com.aiexamhub.exam.controller;

import com.aiexamhub.exam.dto.Member;
import com.aiexamhub.exam.service.MemberService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ai-exam-hub")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/login")
    @ResponseBody
    public String loginProc(@RequestBody Member form , HttpServletResponse response , Model model){

        Member member = memberService.EmailLogin(form , response , model);

        return member.getRes();
    }

    @GetMapping("/mypage")
    public String mypage(ServletRequest servletRequest , Model model){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        model.addAttribute("isLogin" , (Boolean)req.getAttribute("isLogin"));

        String memberCode = (String)req.getAttribute("memberCode");


        return "/view/mypage/repositories";
    }

}
