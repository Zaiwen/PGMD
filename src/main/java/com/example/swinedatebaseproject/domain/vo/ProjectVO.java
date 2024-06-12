package com.example.swinedatebaseproject.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVO {

    private String project;

    private String phenotypes;

    private String experimentalDesign;

    private String country;

    private String category;

    private String breed;

    private String growthStage;

    private String isolationLocation;

    private String age;

    private String weight;

    private String sex;

    private String sequencingTool;

    private String title;

    private String link;

    private String releaseDate;

    private String description;

    private String assayType;

    private int totalNumbersOfRuns;
}
