package com.example.swinedatebaseproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.swinedatebaseproject.domain.NutrientComposition;
import com.example.swinedatebaseproject.service.NutrientCompositionService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NutrientMapper extends BaseMapper<NutrientComposition> {

    /**
     * @param bio_project
     * @return
     */
    List<NutrientCompositionService> getNutrientCompositionByBioProject(@Param("bio_project") String bio_project);
}
