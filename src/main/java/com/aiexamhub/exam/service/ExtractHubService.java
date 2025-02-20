package com.aiexamhub.exam.service;

import com.aiexamhub.exam.dto.ExtractHub;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

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

}
