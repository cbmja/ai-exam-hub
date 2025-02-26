package com.aiexamhub.exam.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExtractQuestion {

    private int extractQuestionCode = -1;   // 식별자
    private String examOrgCode = "";        // 출제기관코드
    private String examCateCode = "";       // 시험 종류 코드
    private int examYear = -1;          // 시험 년도
    private int examMonth = -1;         // 시험 월
    private String subjectCode = "";        // 과목코드
    private String examType = "";           // 시험 타입 / A B odd홀수 even짝수 x없음
    private int extractHubCode = -1;    // 저장소 코드
    private int questionNo = -1;        // 문제 번호
    private String question = "";           // 문제
    private String questionSub = "";        // 문제 보기
    private String option1 = "";            // 선택지1
    private String option2 = "";            // 선택지2
    private String option3 = "";            // 선택지3
    private String option4 = "";            // 선택지4
    private String option5 = "";            // 선택지5
    private LocalDateTime createdAt;            // 생성일
    private LocalDateTime updatedAt;            // 수정일
    private String comment;
    private int commonTextOptionCode;

    private String subjectDetailCode;
    private String commonTextOptionContent;
    private String questionNoStr;

    private String err="";

    private String examOrgName;
    private String examCateName;
    private String subjectName;
    private String subjectDetailName = "";
    private String examTypeName = "";


}
