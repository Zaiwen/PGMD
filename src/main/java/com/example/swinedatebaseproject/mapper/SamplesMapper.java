package com.example.swinedatebaseproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.swinedatebaseproject.domain.Project;
import com.example.swinedatebaseproject.domain.Samples;
import com.example.swinedatebaseproject.domain.dto.SearchDataFuzzyDTO;
import com.example.swinedatebaseproject.domain.dto.SearchFormDTO;
import com.example.swinedatebaseproject.domain.vo.LefseVO;
import com.example.swinedatebaseproject.domain.vo.MicrobialVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author s1mple
* @description 针对表【s1mple】的数据库操作Mapper
* @createDate 2023-02-26 00:20:22
* @Entity com.example.swinedatebaseproject.domain.s1mple
*/
@Mapper
public interface SamplesMapper extends BaseMapper<Samples> {
    List<LefseVO> findLefseDataBySample(
            @Param("bioSample") String bioSample,
            @Param("currentPage") Integer currentPage,
            @Param("pageSize") Integer pageSize
    );

    List<MicrobialVO> findMicrobialDataBySample(@Param("name") String name, @Param("offset") int offset, @Param("pageSize") Integer pageSize);

//    int getLefseCountByBioSample(String bioSample);

    int getNutrientCompositionCountByBioSample(String bioSample);

//    int getProjectCountByBioSample(String bioSample);

    int getMicrobialCountByBioSample(String bioSample);

    List<Samples> searchSample(@Param("type") String type, @Param("breed") String breed, @Param("growthStage") String growthStage, @Param("isolationLocation") String isolationLocation, @Param("experimentalDesign") String experimentalDesign);


    List<Samples> searchSampleByName(@Param("name")String name);

    List<Samples> searchSampleInfo(SearchDataFuzzyDTO dto);

    List<String> getGroupsByBioProject(@Param("name") String name);
}




