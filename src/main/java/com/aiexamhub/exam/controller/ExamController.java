package com.aiexamhub.exam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ai-exam-hub")
@RequiredArgsConstructor
public class ExamController {

    @GetMapping("/exam")
    public String exam(){
        return "/view/exam/exam";
    }

}
