package com.example.swinedatebaseproject.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchFormDTO {
    private List<String> countries;
    private AnimalData boar;
    private AnimalData sow;
    private AnimalData piglet;
    private AnimalData growingFinishingPigs;

}


