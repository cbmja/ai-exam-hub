package com.aiexamhub.exam.controller;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ai-exam-hub")
@RequiredArgsConstructor
public class IndexController {

    // ok-02/25---------------------------------------------------------------------------------------------------------
    @GetMapping("/index")
    public String index(ServletRequest servletRequest , Model model){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        model.addAttribute("isLogin" , (Boolean)req.getAttribute("isLogin"));
        return "view/layout/index";
    }

}
