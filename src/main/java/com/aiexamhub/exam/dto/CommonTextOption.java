package com.aiexamhub.exam.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommonTextOption {

    private int commonTextOptionCode;
    private String commonTextOptionContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String examCateCode;
    private String examType;
    private int examYear;
    private int examMonth;
    private String subjectCode;
    private String subjectDetailCode;
    private String questionNoStr;


    private int hubCode;
    private int sqnum;

    private String err = "";


}
