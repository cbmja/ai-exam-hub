package com.aiexamhub.exam.mapper;

import com.aiexamhub.exam.dto.SubjectDetail;

import java.util.List;

public interface SubjectDetailMapper {

    List<SubjectDetail> selectBySubjectCode(String subjectCode);
    SubjectDetail selectBySubjectDetailCode(String subjectDetailCode);
}
