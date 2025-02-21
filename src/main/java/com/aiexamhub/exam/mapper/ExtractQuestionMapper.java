package com.aiexamhub.exam.mapper;

import com.aiexamhub.exam.dto.ExtractQuestion;
import com.aiexamhub.exam.dto.Search;

import java.util.List;

public interface ExtractQuestionMapper {

    int save(ExtractQuestion form);

    List<ExtractQuestion> selectByExtractHubCode(Search search);

}
