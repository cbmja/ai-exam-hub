package com.aiexamhub.exam.dto;

import lombok.Data;

@Data
public class Subject {

    private String subjectCode;
    private String subjectName;
    private int subCnt;

    private String err="";
}
