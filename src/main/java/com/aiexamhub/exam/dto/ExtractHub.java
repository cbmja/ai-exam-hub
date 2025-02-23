package com.aiexamhub.exam.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExtractHub {

    private int extractHubCode;
    private String extractHubName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String memberCode;
    private String comment;

    private String search = "";
    private String err = "";
    private String sort = "DESC";
    private int page = 1;

}
