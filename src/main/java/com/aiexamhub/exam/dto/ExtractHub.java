package com.aiexamhub.exam.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExtractHub {

    private int extractHubCode;
    private String extractHubName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userId;

    private String err = "";

}
