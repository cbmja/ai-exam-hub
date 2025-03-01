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

    // 03/01 1차 ok-----------------------------------------------------------------------------------------------------
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

    public SubjectDetail selectBySubjectDetailCode(String subjectDetailCode){
        SubjectDetail subjectDetail;
        try {
            subjectDetail = sql.selectOne("com.aiexamhub.exam.mapper.SubjectDetailMapper.selectBySubjectDetailCode" , subjectDetailCode);
            if(subjectDetail == null){
                subjectDetail = new SubjectDetail();
                subjectDetail.setErr("err");
            }
        }catch (Exception e){
            e.printStackTrace();
            subjectDetail = new SubjectDetail();
            subjectDetail.setErr("err");
        }
        return subjectDetail;
    }

}
