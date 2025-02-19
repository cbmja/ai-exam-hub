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

import java.time.Year;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        int currentYear = Year.now().getValue();

        // 1900년부터 현재 년도까지의 숫자를 포함하는 리스트 생성
        List<Integer> yearList = IntStream.rangeClosed(1900, currentYear)  // 1900~현재 년도 범위
                .boxed()  // IntStream을 Integer로 변환
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        model.addAttribute("yearList" , yearList);
        model.addAttribute("examCateList" , examCateList);
        model.addAttribute("title" , "문제_추출");
        return "view/director/extract";
    }


    @GetMapping("/examInfo")
    @ResponseBody
    public Map<String , Object> getExamOrg(@RequestParam(name="examCateCode", defaultValue = "") String examCateCode){

        Map<String , Object> res = new HashMap<>();

        if(examCateCode.isBlank()){
            System.out.println("no request param");
        }

        // 출제 기관 리스트
        List<ExamOrg> examOrgList = examOrgService.selectByExamCateCode("."+examCateCode);
        if(examOrgList == null || examOrgList.isEmpty() || examOrgList.get(0).getErr().equals("err")){
            // 에러
            System.out.println("sql err");
        }

        // 시험 과목 리스트
        List<Subject> subjectList = subjectService.selectByExamCateCode("."+examCateCode);
        if(subjectList == null || subjectList.isEmpty() || subjectList.get(0).getErr().equals("err")){
            // 에러
            System.out.println("sql err");
        }

        res.put("examOrgList" , examOrgList);
        res.put("subjectList" , subjectList);
        return res;
    }


}
