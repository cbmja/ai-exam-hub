package com.aiexamhub.exam.service;

import com.aiexamhub.exam.dto.Member;
import com.aiexamhub.exam.util.LoginUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final SqlSessionTemplate sql;
    private final LoginUtil loginUtil;

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    public Member EmailLogin(Member form , HttpServletResponse response , Model model){
        Member member;
        try{
            member = sql.selectOne("com.aiexamhub.exam.mapper.MemberMapper.findById" , form.getMemberId());

            if(member == null){ // id 없음
                member = new Member();
                member.setRes("wrongId");
            }else{
                if(member.getMemberPw().equals(form.getMemberPw())){ // id , pw 일치

                    String ecCookie = loginUtil.encrypt(member.getMemberCode());

                    if(ecCookie.equals("err")){
                        member = new Member();
                        member.setRes("err");
                        return member;
                    }

                    Cookie idCookie = new Cookie("idCookie", ecCookie);
                    // 쿠키 설정
                    idCookie.setHttpOnly(true);
                    idCookie.setSecure(false);
                    idCookie.setPath("/");
                    idCookie.setMaxAge(3 * 24 * 60 * 60); // 3일 유지

                    response.addCookie(idCookie);

                    member.setRes("success");

                    model.addAttribute("isLogin" , true);

                }else{ // id 있음 , pw 틀림
                    member.setRes("wrongPw");
                }
            }
            return member;
        }catch (Exception e){
            e.printStackTrace();
            member = new Member();
            member.setRes("err");
            return member;
        }
    }

}
