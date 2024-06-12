package com.example.swinedatebaseproject.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountRunsVO {
    private int countProject;
    private int countRuns;
    private String country;
}
