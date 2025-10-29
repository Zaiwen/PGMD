package com.example.swinedatebaseproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.swinedatebaseproject.domain.Microbe;
import com.example.swinedatebaseproject.domain.Project;
import com.example.swinedatebaseproject.domain.dto.SearchDataFuzzyDTO;
import com.example.swinedatebaseproject.domain.vo.*;
import com.example.swinedatebaseproject.response.ResponseResult;
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
public interface ProjectMapper extends BaseMapper<Project> {

    List<ProjectDTO> findProjectDetails(@Param("name") String name, @Param("currentPage") Integer currentPage, @Param("pageSize") int pageSize);
    List<Project> findProjectsByCondition(Project project);

    int getLefseCountByBioProject(String bioProject);

    int getNutrientCompositionCountByBioProject(String bioProject);

    int getSampleRunsCountByBioProject(String bioProject);

    int getMicrobialCountByBioProject(String bioProject);

    List<CountCategoryVO> countCategory();

    List<CountCountryVO> countCountry();

    List<CountRunsVO> countRuns();

    List<CountIsolationVO> countIsolation();

    List<CountryProjectCount> getCountryProjectCounts();
    List<ProjectVO> searchProject(@Param("type") String type, @Param("breed") String breed, @Param("growthStage") String growthStage, @Param("phenotypes") String phenotypes, @Param("isolationLocation") String isolationLocation,@Param("country") String country);

    List<LefseVO> getLefseByBioProject(String name);

    List<ShowDataVO> getShowDataVOs();

    List<ProjectVO> searchProjectByName(@Param("name") String name);

    List<ProjectVO> searchProjectInfo(SearchDataFuzzyDTO dto);

    List<Microbe> searchMicrobeByName(@Param("name") String name);
}




