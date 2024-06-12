package com.example.swinedatebaseproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.swinedatebaseproject.domain.Notice;

import java.util.List;

/**
* @author s1mple
* @description 针对表【notice】的数据库操作Mapper
* @createDate 2023-02-26 16:17:59
* @Entity com.example.swinedatebaseproject.domain.Notice
*/
public interface NoticeMapper extends BaseMapper<Notice> {


    List<Notice> selectLists();
}




