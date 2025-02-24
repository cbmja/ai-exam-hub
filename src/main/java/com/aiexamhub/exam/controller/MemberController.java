package com.aiexamhub.exam.controller;

import com.aiexamhub.exam.dto.*;
import com.aiexamhub.exam.service.*;
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
    private final ExamOrgService examOrgService;
    private final ExamCateService examCateService;
    private final SubjectService subjectService;
    private final SubjectDetailService subjectDetailService;



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

    @GetMapping("/mypage/repository/{hubCode}")
    public String questions(ServletRequest servletRequest , Model model , @PathVariable(name = "hubCode")int hubCode){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        model.addAttribute("isLogin" , (Boolean)req.getAttribute("isLogin"));

        String memberCode = (String)req.getAttribute("memberCode");

        List<ExtractQuestion> list = extractQuestionService.selectByExtractHubCode(hubCode , "ASC");


        for(ExtractQuestion eq : list){

            if(eq.getExamType().equals("even")){
                eq.setExamTypeName("짝수형");
            }else if(eq.getExamType().equals("odd")){
                eq.setExamTypeName("홀수형");
            }else if(eq.getExamType().equals("1")){
                eq.setExamTypeName("1형");
            }else if(eq.getExamType().equals("2")){
                eq.setExamTypeName("2형");
            }else if(eq.getExamType().equals("x")){
                eq.setExamTypeName("X");
            }

            if(eq.getSubjectDetailName().isEmpty()){
                eq.setSubjectDetailName("공통");
            }
        }


        ExtractHub extractHub = extractHubService.selectByExtractHubCode(hubCode);
        model.addAttribute("extractHub" , extractHub);
        model.addAttribute("list" , list);
        return "/view/mypage/questions";
    }

}
