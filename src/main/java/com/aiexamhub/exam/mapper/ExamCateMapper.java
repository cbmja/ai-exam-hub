package com.aiexamhub.exam.mapper;

import com.aiexamhub.exam.dto.ExamCate;

public interface ExamCateMapper {

    ExamCate selectAll();

    ExamCate selectByExamCateCode(String ExamCateCode);

}
