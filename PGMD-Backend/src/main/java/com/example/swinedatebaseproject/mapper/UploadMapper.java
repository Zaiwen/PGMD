package com.example.swinedatebaseproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.swinedatebaseproject.domain.Upload;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadMapper extends BaseMapper<Upload> {

    public void insertUpload(Upload upload);

}
