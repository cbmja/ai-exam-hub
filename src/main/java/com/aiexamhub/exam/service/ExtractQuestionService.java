package com.aiexamhub.exam.service;

import com.aiexamhub.exam.dto.ExtractQuestion;
import com.aiexamhub.exam.dto.Search;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            e.printStackTrace();
            res = -1;
            return res;
        }
    }


    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 03/01 2차 ok-----------------------------------------------------------------------------------------------------
    public int CountByExtractHubCode(int hubCode){
        int res = -1;
        try{
            res = sql.selectOne("com.aiexamhub.exam.mapper.ExtractQuestionMapper.CountByExtractHubCode" , hubCode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 03/01 2차 ok-----------------------------------------------------------------------------------------------------
    public List<ExtractQuestion> selectByExtractHubCode(int hubCode , String sort){
        List<ExtractQuestion> list = null;

        try{
            Search search = new Search();
            search.setExtractHubCode(hubCode);
            search.setSort(sort);
            list = sql.selectList("com.aiexamhub.exam.mapper.ExtractQuestionMapper.selectByExtractHubCode",search);
            if(list == null){
                list = new ArrayList<>();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        if(list == null){
            list = new ArrayList<>();
            ExtractQuestion e = new ExtractQuestion();
            e.setErr("err");
            list.add(e);
        }

        return list;
    }

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 03/01 2차 ok-----------------------------------------------------------------------------------------------------
    public ExtractQuestion selectByExtractQuestionCode(int extractQuestionCode){
        ExtractQuestion extractQuestion = null;
        try{
            extractQuestion = sql.selectOne("com.aiexamhub.exam.mapper.ExtractQuestionMapper.selectByExtractQuestionCode",extractQuestionCode);

        }catch (Exception e){
            e.printStackTrace();
        }

        if(extractQuestion == null){
            extractQuestion = new ExtractQuestion();
            extractQuestion.setErr("err");
        }
        return extractQuestion;

    }
    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    public int updateQuestion(ExtractQuestion form){

        int res = -1;
        try {
            res = sql.update("com.aiexamhub.exam.mapper.ExtractQuestionMapper.updateQuestion",form);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;

    }

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    public int delete(int extractQuestionCode){

        int res = -1;
        try {
            res = sql.delete("com.aiexamhub.exam.mapper.ExtractQuestionMapper.delete",extractQuestionCode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;

    }

}
