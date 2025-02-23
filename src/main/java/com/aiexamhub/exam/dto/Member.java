package com.aiexamhub.exam.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {

    private String memberCode;
    private String memberId;
    private String memberPw;
    private String loginType;
    private String email;
    private String socialSub;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String res = "";

}
