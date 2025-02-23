package com.aiexamhub.exam.controller;

import com.aiexamhub.exam.dto.Member;
import com.aiexamhub.exam.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ai-exam-hub")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;


    @PostMapping("/login")
    @ResponseBody
    public String loginProc(@RequestBody Member form , HttpServletResponse response){

        Member member = memberService.EmailLogin(form , response);

        return member.getRes();
    }

}
