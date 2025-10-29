package com.example.swinedatebaseproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Upload {
    private Long fileId;
    private String email;
    private String type;
    private String paperTitle;
    private String projectId;
    private String introduction;
    private String metaDataUrl;
    private String classificationUrl;
    private String lefseUrl;
    private String picrust2Url;
    private LocalDateTime createTime;
    private String createBy;
    private String feedback;
    private String analysisResult;


}