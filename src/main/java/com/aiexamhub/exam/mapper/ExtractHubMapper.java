package com.aiexamhub.exam.mapper;

import com.aiexamhub.exam.dto.ExtractHub;
import com.aiexamhub.exam.dto.Page;

import java.util.List;

public interface ExtractHubMapper {

    ExtractHub selectByExtractHubCode(int ExtractHubCode);

    int getTotal(Page form);

    List<ExtractHub> getRepositories(Page form);

}
