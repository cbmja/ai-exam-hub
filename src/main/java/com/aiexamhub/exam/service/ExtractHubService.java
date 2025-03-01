package com.aiexamhub.exam.service;

import com.aiexamhub.exam.dto.ExtractHub;
import com.aiexamhub.exam.dto.Page;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtractHubService {

    private final SqlSessionTemplate sql;
    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 03/01 2차 ok-----------------------------------------------------------------------------------------------------
    public ExtractHub selectByExtractHubCode(int extractHubCode){
        ExtractHub extractHub = null;
        try{
            extractHub = sql.selectOne("com.aiexamhub.exam.mapper.ExtractHubMapper.selectByExtractHubCode" , extractHubCode);

        }catch (Exception e){
            e.printStackTrace();
            extractHub = new ExtractHub();
            extractHub.setErr("err");
        }
        return extractHub;
    }

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    public int save(ExtractHub form){
        int res = -1;
        try {
            res = sql.insert("com.aiexamhub.exam.mapper.ExtractHubMapper.save" , form);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;

    }

    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 03/01 2차 ok-----------------------------------------------------------------------------------------------------
    public int getTotal(Page form){

        int total = -1;
        try{
            total = sql.selectOne("com.aiexamhub.exam.mapper.ExtractHubMapper.getTotal" , form);
        }catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }
    // 02/25 1차 ok-----------------------------------------------------------------------------------------------------
    // 03/01 2차 ok-----------------------------------------------------------------------------------------------------
    public List<ExtractHub> getRepositories(Page form){
        
        List<ExtractHub> list = null;
        try{
            list = sql.selectList("com.aiexamhub.exam.mapper.ExtractHubMapper.getRepositories" , form);
            if(list == null){
                list = new ArrayList<>();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(list == null){
            list = new ArrayList<>();
            ExtractHub e= new ExtractHub();
            e.setErr("Err");
            list.add(e);
        }

        return list;
    }



}
