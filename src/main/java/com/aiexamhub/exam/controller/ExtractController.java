package com.aiexamhub.exam.controller;

import com.aiexamhub.exam.dto.ExamCate;
import com.aiexamhub.exam.service.ExamCateService;
import com.aiexamhub.exam.service.ExamOrgService;
import com.aiexamhub.exam.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ai/extract")
@RequiredArgsConstructor
public class ExtractController {

    private final ExamCateService examCateService;
    private final ExamOrgService examOrgService;
    private final SubjectService subjectService;

    @GetMapping("/form")
    public String testing(Model model){


        List<ExamCate> examCateList = examCateService.selectAll();
        if(examCateList == null || examCateList.isEmpty() || examCateList.get(0).getErr().equals("err")){
            // 에러
            System.out.println("sql err");
        }

        model.addAttribute("examCateList" , examCateList);
        model.addAttribute("title" , "문제_추출");
        return "view/director/extract";
    }







}
