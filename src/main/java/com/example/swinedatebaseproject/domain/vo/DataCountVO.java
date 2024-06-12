package com.example.swinedatebaseproject.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataCountVO {

    private int lefseCount;
    private int nutrientCompositionCount;
    private int microbialCount;
    private int sampleRunsCount;
    private int projectCount;

}