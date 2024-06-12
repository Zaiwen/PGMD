package com.example.swinedatebaseproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.swinedatebaseproject.domain.Counter;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterMapper extends BaseMapper<Counter> {
    @Select("SELECT * FROM counter WHERE page = #{page}")
    Counter selectByPage(String page);
}
