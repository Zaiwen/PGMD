package com.example.swinedatebaseproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.swinedatebaseproject.domain.Microbial;
import com.example.swinedatebaseproject.domain.vo.CountLevelVO;
import com.example.swinedatebaseproject.domain.vo.GroupVO;
import com.example.swinedatebaseproject.domain.vo.RunsAndCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author s1mple
* @description 针对表【cds】的数据库操作Mapper
* @createDate 2023-02-26 00:20:22
* @Entity com.example.swinedatebaseproject.domain.Cds
*/
@Mapper
public interface MicrobialMapper extends BaseMapper<Microbial> {

    List<CountLevelVO> countLevelByName();

    List<Microbial> getMicrobialByBioProject(@Param("name") String name);

    List<RunsAndCount> selectRunsByGroupsAndLevels(@Param("group")String group, @Param("name") String name);

    List<GroupVO> getSpecificTaxonomyByRuns(@Param("runs")String run, @Param("group") String group, @Param("project") String project);

    String getMicrobialNameBySpecificTaxonomy(@Param("taxonomy")String taxonomy);

    List<Microbial> getMicrobial(@Param("name") String name);
}




