package com.aiexamhub.exam.controller;

import com.aiexamhub.exam.dto.ExtractHub;
import com.aiexamhub.exam.dto.ExtractQuestion;
import com.aiexamhub.exam.dto.Member;
import com.aiexamhub.exam.dto.Page;
import com.aiexamhub.exam.service.*;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
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


    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 03/01 2차 ok-----------------------------------------------------------------------------------------------------
    // 로그인
    @PostMapping("/login")
    @ResponseBody
    public String loginProc(@RequestBody Member form , HttpServletResponse response , Model model){

        Member member = memberService.EmailLogin(form , response , model);

        return member.getRes();
    }

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 03/01 2차 ok-----------------------------------------------------------------------------------------------------
    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        String viewPath = "redirect:/ai-exam-hub/index";
        try{
            // 기존 쿠키를 찾기
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("idCookie")) {
                        // 쿠키 삭제: 만료 시간을 과거로 설정하여 삭제
                        cookie.setMaxAge(0);  // 0으로 설정하면 쿠키는 즉시 만료됩니다.
                        cookie.setPath("/");   // 쿠키가 설정된 경로도 지정합니다. 일반적으로 '/'로 설정합니다.
                        response.addCookie(cookie);  // 수정된 쿠키를 응답에 추가
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            viewPath = "/view/util/errer";
        }


        return viewPath;
    }

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 03/01 2차 ok-----------------------------------------------------------------------------------------------------
    // 내 저장소 목록
    @GetMapping("/mypage/repository")
    public String mypage(ServletRequest servletRequest , Model model , @RequestParam(name = "page" ,defaultValue = "0") int page , @RequestParam(name = "search" ,defaultValue = "") String search){
        String viewPath = "/view/mypage/repositories";
        try {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            model.addAttribute("isLogin", (Boolean) req.getAttribute("isLogin"));

            String memberCode = (String) req.getAttribute("memberCode");

            Page p = new Page();
            p.setSearch(search);
            p.setMemberCode(memberCode);
            int total = extractHubService.getTotal(p);

            if(total <= -1){
                return "/view/util/error";
            }

            Page form = new Page(page, total); //현재 페이지

            form.setMemberCode(memberCode);
            form.setSearch(search); // 검색어
            form.setSort("DESC"); // 정렬

            List<ExtractHub> list = extractHubService.getRepositories(form);

            if(!list.isEmpty() && list.get(0).getErr().equals("err")){

                return "/view/util/error";
            }

            for (ExtractHub eh : list) {
                int count = extractQuestionService.CountByExtractHubCode(eh.getExtractHubCode());
                if(count < 0){
                    return "/view/util/error";
                }
                eh.setCount(count);
            }

            model.addAttribute("list", list);
            model.addAttribute("page", form);
        }catch (Exception e){
            e.printStackTrace();
            viewPath = "/view/util/error";
        }

        return viewPath;
    }

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 03/01 2차 ok-----------------------------------------------------------------------------------------------------
    // 내 저장소(저장소에 저장된 문제 목록)
    @GetMapping("/mypage/repository/{hubCode}")
    public String questions(ServletRequest servletRequest , Model model , @PathVariable(name = "hubCode")int hubCode){
        String viewPath = "/view/mypage/questions";
        try {

            HttpServletRequest req = (HttpServletRequest) servletRequest;
            model.addAttribute("isLogin", (Boolean) req.getAttribute("isLogin"));

            String memberCode = (String) req.getAttribute("memberCode");

            List<ExtractQuestion> list = extractQuestionService.selectByExtractHubCode(hubCode, "ASC");

            if(!list.isEmpty() && list.get(0).getErr().equals("err")){
                return "/view/util/error";
            }


            for (ExtractQuestion eq : list) {

                if (eq.getExamType().equals("even")) {
                    eq.setExamTypeName("짝수형");
                } else if (eq.getExamType().equals("odd")) {
                    eq.setExamTypeName("홀수형");
                } else if (eq.getExamType().equals("1")) {
                    eq.setExamTypeName("1형");
                } else if (eq.getExamType().equals("2")) {
                    eq.setExamTypeName("2형");
                } else if (eq.getExamType().equals("x")) {
                    eq.setExamTypeName("타입X");
                }
                if (eq.getSubjectDetailName().isEmpty()) {
                    eq.setSubjectDetailName("공통");
                }
            }


            ExtractHub extractHub = extractHubService.selectByExtractHubCode(hubCode);
            if(extractHub != null) {
                if (extractHub.getErr().equals("err")) {
                    return "/view/util/error";
                }
            }
            model.addAttribute("extractHub", extractHub);
            model.addAttribute("list", list);

        }catch (Exception e){
            e.printStackTrace();
            viewPath = "/view/util/error";
        }
        return viewPath;
    }

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 03/01 2차 ok-----------------------------------------------------------------------------------------------------
    // 문제 상세
    @GetMapping("/question/{questionCode}")
    @ResponseBody
    public ExtractQuestion getQuestion(@PathVariable(name = "questionCode")int questionCode){


        ExtractQuestion extractQuestion = extractQuestionService.selectByExtractQuestionCode(questionCode);

        return extractQuestion;
    }

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 문제 수정
    @PostMapping("/question/edit")
    @ResponseBody
    public int editQuestion(@RequestBody ExtractQuestion form){


        return extractQuestionService.updateQuestion(form);
    }

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 문제 삭제
    @PostMapping("/question/delete")
    @ResponseBody
    public int deleteQuestion(@RequestBody ExtractQuestion form){


        return extractQuestionService.delete(form.getExtractQuestionCode());
    }

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 저장소 생성
    @PostMapping("/repository/add")
    @ResponseBody
    public int createRepository(ServletRequest servletRequest,@RequestBody ExtractHub form){
        int res = -1;
        try{
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            String memberCode = (String)req.getAttribute("memberCode");

            form.setMemberCode(memberCode);
            res = extractHubService.save(form);
        }catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }

}
