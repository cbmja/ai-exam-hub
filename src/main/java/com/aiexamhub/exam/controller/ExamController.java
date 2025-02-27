package com.aiexamhub.exam.controller;

import com.aiexamhub.exam.dto.ExtractHub;
import com.aiexamhub.exam.dto.ExtractQuestion;
import com.aiexamhub.exam.service.CommonTextOptionService;
import com.aiexamhub.exam.service.ExtractHubService;
import com.aiexamhub.exam.service.ExtractQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/ai-exam-hub")
@RequiredArgsConstructor
public class ExamController {

    private final ExtractHubService extractHubService;
    private final ExtractQuestionService extractQuestionService;
    private final CommonTextOptionService commonTextOptionService;

    @GetMapping("/exam/{hubCode}")
    public String exam(@PathVariable(name="hubCode") int hubCode , Model model){

        // hubCode에 해당하는 ExtractHub 객체를 가져옴
        ExtractHub myHub = extractHubService.selectByExtractHubCode(hubCode);

        // 해당 hubCode에 연결된 ExtractQuestion 리스트를 "ASC" 순으로 가져옴
        List<ExtractQuestion> list = extractQuestionService.selectByExtractHubCode(hubCode, "ASC");

        // questionNo 기준으로 정렬
        Collections.sort(list, Comparator.comparingInt(ExtractQuestion::getQuestionNo));


        // 공통 보기 코드 중복 제거를 위해 Set 사용
        Set<Integer> commonOptionCodeSet = new HashSet<>();
        for (ExtractQuestion question : list) {

            commonOptionCodeSet.add(question.getCommonTextOptionCode());
        }

        int coCode = -1;

        for(ExtractQuestion question : list){

            if(coCode == question.getCommonTextOptionCode()){
                question.setCommonTextOptionContent("");
            }else{
                coCode = question.getCommonTextOptionCode();
            }

            if (question.getExamType().equals("even")) {
                question.setExamTypeName("짝수형");
            } else if (question.getExamType().equals("odd")) {
                question.setExamTypeName("홀수형");
            } else if (question.getExamType().equals("1")) {
                question.setExamTypeName("1형");
            } else if (question.getExamType().equals("2")) {
                question.setExamTypeName("2형");
            } else if (question.getExamType().equals("x")) {
                question.setExamTypeName("X");
            }
            if (question.getSubjectDetailName().isEmpty()) {
                question.setSubjectDetailName("공통");
            }

        }




        model.addAttribute("myHub" , myHub);
        model.addAttribute("list" , list);

        return "/view/exam/exam";
    }


}
