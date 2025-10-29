package com.example.swinedatebaseproject.mapper;

import com.example.swinedatebaseproject.domain.Administrator;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author s1mple
* @description 针对表【administrator】的数据库操作Mapper
* @createDate 2022-12-01 19:25:27
* @Entity com.example.swinedatebaseproject.domain.Administrator
*/
public interface AdministratorMapper extends BaseMapper<Administrator> {
    boolean authenticate(@Param("name") String name, @Param("password") String password);
}




