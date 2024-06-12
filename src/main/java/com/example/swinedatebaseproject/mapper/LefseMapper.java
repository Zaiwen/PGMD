package com.example.swinedatebaseproject.mapper;

import com.example.swinedatebaseproject.domain.vo.LefseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LefseMapper {

    List<LefseVO> findSimilarProjects(String name);
}
