package com.aiexamhub.exam.controller;

import com.aiexamhub.exam.dto.ExamCate;
import com.aiexamhub.exam.dto.ExamOrg;
import com.aiexamhub.exam.dto.Subject;
import com.aiexamhub.exam.service.ExamCateService;
import com.aiexamhub.exam.service.ExamOrgService;
import com.aiexamhub.exam.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @GetMapping("/examOrg")
    @ResponseBody
    public List<ExamOrg> getExamOrg(@RequestParam(name="examCateCode", defaultValue = "") String examCateCode){

        if(examCateCode.isBlank()){
            System.out.println("no request param");
        }

        List<ExamOrg> examOrgList = examOrgService.selectByExamCateCode("."+examCateCode);
        if(examOrgList == null || examOrgList.isEmpty() || examOrgList.get(0).getErr().equals("err")){
            // 에러
            System.out.println("sql err");
        }

        return examOrgList;
    }

    @GetMapping("/examSubject")
    @ResponseBody
    public List<Subject> getSubject(@RequestParam(name="examOrgCode", defaultValue = "") String examCateCode){

        if(examCateCode.isBlank()){
            System.out.println("no request param");
        }

        List<Subject> subjectList = subjectService.selectByExamCateCode("."+examCateCode);
        if(subjectList == null || subjectList.isEmpty() || subjectList.get(0).getErr().equals("err")){
            // 에러
            System.out.println("sql err");
        }

        return subjectList;
    }



}
