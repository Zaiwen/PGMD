package com.example.swinedatebaseproject.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private String bioProject;
    private String phenotypes;
    private String phenotypesInfo;
    private String experimentalDesign;
    private String country;
    private String category;
    private String breed;
    private String growthStage;
    private String isolationLocation;
    private String age;
    private String weight;
    private String sex;
    private String title;
    private String description;
    private String link;
    private String sequencingTool;
    private String releaseDate;
    private String assayType;
    private String sampleRuns;
    private String microbialName;
    private Float dm;
    private Float cp;
    private Float ee;
    private Float cf;
    private Float nfe;
    private Float ash;
    private Float ndf;
    private Float adf;
    private Float starch;
    private Float ca;
    private Float tp;
    private Float ep;
    private int microbialId;
    private String specificTaxonomy;
    private String level;
    private int microbialCount;
    private float abundance;

}
