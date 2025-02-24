package com.aiexamhub.exam.mapper;

import com.aiexamhub.exam.dto.ExamOrg;

import java.util.List;

public interface ExamOrgMapper {

    List<ExamOrg> selectAll();
    List<ExamOrg> selectByExamCateCode(String examCateCode);

    ExamOrg selectByExamOrgCode(String examOrgCode);

}
