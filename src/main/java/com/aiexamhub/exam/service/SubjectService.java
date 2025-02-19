package com.aiexamhub.exam.service;

import com.aiexamhub.exam.dto.Subject;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SqlSessionTemplate sql;

    public List<Subject> selectAll(){
        List<Subject> subjcetList;
        try{
            subjcetList = sql.selectList("com.aiexamhub.exam.mapper.SubjectMapper.selectAll");
            return subjcetList;
        }catch (Exception e){
            e.printStackTrace();
            subjcetList = new ArrayList<>();
            Subject subject = new Subject();
            subject.setErr("err");
            subjcetList.add(subject);
            return subjcetList;
        }
    }

    public List<Subject> selectByExamCateCode(String examCateCode){
        List<Subject> subjcetList;
        try{
            subjcetList = sql.selectList("com.aiexamhub.exam.mapper.SubjectMapper.selectByExamCateCode" , examCateCode);
            subjcetList.sort(Comparator.comparing(Subject::getSubjectName));
            return subjcetList;
        }catch (Exception e){
            e.printStackTrace();
            subjcetList = new ArrayList<>();
            Subject subject = new Subject();
            subject.setErr("err");
            subjcetList.add(subject);
            return subjcetList;
        }
    }

}
