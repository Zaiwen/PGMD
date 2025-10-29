package com.example.swinedatebaseproject.service.impl;


import com.example.swinedatebaseproject.mapper.NutrientMapper;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.service.NutrientCompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NutrientCompositionServiceImpl implements NutrientCompositionService {

    @Autowired
    private NutrientMapper nutrientMapper;


    @Override
    public ResponseResult searchNutrientComposition(String bio_project) {
        List<NutrientCompositionService> list = nutrientMapper.getNutrientCompositionByBioProject(bio_project);
        return ResponseResult.success(list);
    }
}
