package com.aiexamhub.exam.service;

import com.aiexamhub.exam.dto.ExtractHub;
import com.aiexamhub.exam.dto.Page;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtractHubService {

    private final SqlSessionTemplate sql;

    public ExtractHub selectByExtractHubCode(int extractHubCode){
        ExtractHub extractHub;
        try{
            extractHub = sql.selectOne("com.aiexamhub.exam.mapper.ExtractHubMapper.selectByExtractHubCode" , extractHubCode);

            if(extractHub == null){
                extractHub = new ExtractHub();
                extractHub.setErr("noEle");
            }
            return extractHub;
        }catch (Exception e){
            e.printStackTrace();
            extractHub = new ExtractHub();
            extractHub.setErr("err");
            return extractHub;
        }
    }


    public int getTotal(Page form){

        int total = sql.selectOne("com.aiexamhub.exam.mapper.ExtractHubMapper.getTotal" , form);
        return total;
    }

    public List<ExtractHub> getRepositories(Page form){
        return sql.selectList("com.aiexamhub.exam.mapper.ExtractHubMapper.getRepositories" , form);
    }

}
