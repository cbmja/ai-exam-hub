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

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    @GetMapping("/index")
    public String index(ServletRequest servletRequest , Model model){
        String viewPath = "view/layout/index";
        try{
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            model.addAttribute("isLogin" , (Boolean)req.getAttribute("isLogin"));
        }catch(Exception e){
            e.printStackTrace();
            viewPath = "view/util/error";
        }

        return viewPath;
    }

}
