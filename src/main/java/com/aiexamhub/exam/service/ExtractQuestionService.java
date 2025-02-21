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
            res = -1;
            return res;
        }
    }

    public List<ExtractQuestion> selectByExtractHubCode(int hubCode , String sort){
        List<ExtractQuestion> list ;
        Search search = new Search();
        search.setExtractHubCode(hubCode);
        search.setSort(sort);

        try{
            list = sql.selectList("com.aiexamhub.exam.mapper.ExtractQuestionMapper.selectByExtractHubCode",search);

        }catch (Exception e){
            e.printStackTrace();
            list = new ArrayList<>();
            ExtractQuestion ex = new ExtractQuestion();
            ex.setErr("err");
            list.add(ex);
            return list;
        }
        return list;
    }

}
