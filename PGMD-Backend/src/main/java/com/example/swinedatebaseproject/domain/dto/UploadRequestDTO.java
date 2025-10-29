package com.example.swinedatebaseproject.domain.dto;

import lombok.Data;

@Data
public class UploadRequestDTO {
    private String email;
    private String type;
    private String paperTitle;
    private String projectId;
    private String introduction;
    private String feedback;
    private String analysisResult;

    public UploadRequestDTO(String email, String type, String paperTitle, String projectId, String introduction, String feedback, String analysisResult) {
        this.email = email;
        this.type = type;
        this.paperTitle = paperTitle;
        this.projectId = projectId;
        this.introduction = introduction;
        this.feedback = feedback;
        this.analysisResult = analysisResult;
    }

}