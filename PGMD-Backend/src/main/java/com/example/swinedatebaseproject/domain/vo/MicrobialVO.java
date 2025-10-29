package com.example.swinedatebaseproject.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MicrobialVO {
    private int id;
    private String microbialName;
    private String specificTaxonomy;
    private String level;
    private int count;
    private float abundance;
}
