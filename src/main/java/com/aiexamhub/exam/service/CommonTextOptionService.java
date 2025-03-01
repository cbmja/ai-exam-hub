package com.aiexamhub.exam.service;

import com.aiexamhub.exam.dto.CommonTextOption;
import com.aiexamhub.exam.dto.ExtractQuestion;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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
    // 03/01 1차 ok-----------------------------------------------------------------------------------------------------
    public int save(CommonTextOption form){
        int res = -1;
        try{
            res = sql.insert("com.aiexamhub.exam.mapper.CommonTextOptionMapper.save" , form);
        }catch (Exception e){
            e.printStackTrace();

        }


        if(res > 0){

            List<ExtractQuestion> list = sql.selectList("com.aiexamhub.exam.mapper.ExtractQuestionMapper.selectForCommonOpt" , form);

            for(ExtractQuestion eq : list){
                eq.setCommonTextOptionCode(form.getCommonTextOptionCode());
                int r = sql.update("com.aiexamhub.exam.mapper.ExtractQuestionMapper.updateCommonOtp" , eq);
                if(r <= 0){
                    return -1;
                }
            }

        }else{
            return -1;
        }


        return res;

    }

}
