package com.aiexamhub.exam.service;

import com.aiexamhub.exam.dto.CommonTextOption;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonTextOptionService {

    private final SqlSessionTemplate sql;


    public CommonTextOption selectByCode(int code){
        CommonTextOption co;
        try {
            co = sql.selectOne("com.aiexamhub.exam.mapper.CommonTextOptionMapper.selectByCode" , code);
        }catch (Exception e){
            e.printStackTrace();
            co = new CommonTextOption();
            co.setErr("err");
        }
        return co;

    }

}
