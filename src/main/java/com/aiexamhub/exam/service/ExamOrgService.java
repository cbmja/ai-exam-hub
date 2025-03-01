package com.aiexamhub.exam.service;

import com.aiexamhub.exam.dto.ExamOrg;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamOrgService {

    private final SqlSessionTemplate sql;

    public List<ExamOrg> selectAll(){
        List<ExamOrg> examOrgList;
        try{
            examOrgList = sql.selectList("com.aiexamhub.exam.mapper.ExamOrgMapper.selectAll");
            return examOrgList;
        }catch (Exception e){
            e.printStackTrace();
            examOrgList = new ArrayList<>();
            ExamOrg examOrg = new ExamOrg();
            examOrg.setErr("err");
            examOrgList.add(examOrg);
            return examOrgList;
        }
    }
    // 03/01 1차 ok-----------------------------------------------------------------------------------------------------
    public List<ExamOrg> selectByExamCateCode(String examCateCode){
        List<ExamOrg> examOrgList;
        try{
            examOrgList = sql.selectList("com.aiexamhub.exam.mapper.ExamOrgMapper.selectByExamCateCode",examCateCode);
            examOrgList.sort(Comparator.comparing(ExamOrg::getExamOrgName));
            return examOrgList;
        }catch (Exception e){
            e.printStackTrace();
            examOrgList = new ArrayList<>();
            ExamOrg examOrg = new ExamOrg();
            examOrg.setErr("err");
            examOrgList.add(examOrg);
            return examOrgList;
        }
    }

    public ExamOrg selectByExamOrgCode(String examOrgCode){
        ExamOrg examOrg;
        try{
            examOrg = sql.selectOne("com.aiexamhub.exam.mapper.ExamOrgMapper.selectByExamOrgCode",examOrgCode);
            if(examOrg == null){
                examOrg = new ExamOrg();
                examOrg.setErr("err");
            }
        }catch (Exception e){
            e.printStackTrace();
            examOrg = new ExamOrg();
            examOrg.setErr("err");
        }
        return examOrg;

    }




}
