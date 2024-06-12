package com.example.swinedatebaseproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDataFuzzyDTO {
    private int phenoTypeId;

    private String phenotypes;

    private String experimentalDesign;

    private String country;

    private String category;

    private String breed;

    private String growthStage;

    private String isolationLocation;

    private String sex;
}
