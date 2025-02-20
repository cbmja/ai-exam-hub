package com.aiexamhub.exam.service;

import com.aiexamhub.exam.dto.SubjectDetail;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectDetailService {


    private final SqlSessionTemplate sql;

    public List<SubjectDetail> selectBySubjectCode(String subjectCode){
        List<SubjectDetail> subjcetDetailList;
        try{
            subjcetDetailList = sql.selectList("com.aiexamhub.exam.mapper.SubjectDetailMapper.selectBySubjectCode" , subjectCode);
            return subjcetDetailList;
        }catch (Exception e){
            e.printStackTrace();
            subjcetDetailList = new ArrayList<>();
            SubjectDetail subjectDetail = new SubjectDetail();
            subjectDetail.setErr("err");
            subjcetDetailList.add(subjectDetail);
            return subjcetDetailList;
        }
    }

}
