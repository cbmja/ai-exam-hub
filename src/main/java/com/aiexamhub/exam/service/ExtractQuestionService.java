package com.aiexamhub.exam.service;

import com.aiexamhub.exam.dto.ExtractQuestion;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractQuestionService {

    private final SqlSessionTemplate sql;

    public int save(ExtractQuestion form){
        int res = 0;
        try{
            res = sql.insert("com.aiexamhub.exam.mapper.ExtractQuestionMapper.save" , form);

            return res;
        }catch (Exception e){
            res = -1;
            return res;
        }
    }

}
