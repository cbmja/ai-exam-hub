package com.aiexamhub.exam.service;

import com.aiexamhub.exam.dto.ExamCate;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamCateService {

    private final SqlSessionTemplate sql;
    // 03/01 1차 ok-----------------------------------------------------------------------------------------------------
    public List<ExamCate> selectAll(){
        List<ExamCate> examCateList;
        try{
            examCateList = sql.selectList("com.aiexamhub.exam.mapper.ExamCateMapper.selectAll");
            examCateList.sort(Comparator.comparingInt(ExamCate::getWeight).reversed());
            return examCateList;
        }catch (Exception e){
            e.printStackTrace();
            examCateList = new ArrayList<>();
            ExamCate examCate = new ExamCate();
            examCate.setErr("err");
            examCateList.add(examCate);
            return examCateList;
        }
    }

    public ExamCate selectByExamCateCode(String examCateCode){
        ExamCate examCate;
        try{
            examCate = sql.selectOne("com.aiexamhub.exam.mapper.ExamCateMapper.selectByExamCateCode",examCateCode);
            if(examCate == null){
                examCate = new ExamCate();
                examCate.setErr("err");
            }
        }catch (Exception e){
            e.printStackTrace();
            examCate = new ExamCate();
            examCate.setErr("err");
        }
        return examCate;



    }


}
