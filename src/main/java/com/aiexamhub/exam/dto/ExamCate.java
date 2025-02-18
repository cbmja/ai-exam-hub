package com.aiexamhub.exam.dto;

import lombok.Data;

@Data
public class ExamCate {

    private String examCateCode;
    private String examCateName;
    private int weight;

    private String err="";


}
