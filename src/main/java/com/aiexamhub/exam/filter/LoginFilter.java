package com.aiexamhub.exam.filter;

import com.aiexamhub.exam.service.MemberService;
import com.aiexamhub.exam.util.LoginUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class LoginFilter implements Filter {

    private final LoginUtil loginUtil;
    private final MemberService memberService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        String reqUri = req.getRequestURI();

        Cookie[] cookies = req.getCookies();
        String idCookie = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("idCookie".equals(cookie.getName())) {
                    idCookie = cookie.getValue(); // 원하는 쿠키 반환
                }

            }
        }

        Boolean isLogin = !idCookie.isEmpty();
        servletRequest.setAttribute("isLogin" , isLogin);

        if(!idCookie.isEmpty()){
            idCookie = loginUtil.decrypt(idCookie);
        }

        servletRequest.setAttribute("memberCode" , idCookie);

        filterChain.doFilter(servletRequest, servletResponse);


    }
}
