package com.aiexamhub.exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testingController {

    @GetMapping("/index")
    public String testing(){
        return "view/index";
    }

}
