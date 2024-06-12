package com.example.swinedatebaseproject.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LefseVO {
    private int lefseId;
    private String bioProject;
    private String phenotypesExperimentDesign;
    private String microbialName;
    private String group;
    private float ldaScore;
}
