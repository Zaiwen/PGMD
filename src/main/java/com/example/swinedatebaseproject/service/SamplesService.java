package com.example.swinedatebaseproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.swinedatebaseproject.domain.Samples;
import com.example.swinedatebaseproject.domain.dto.SearchFormDTO;
import com.example.swinedatebaseproject.response.ResponseResult;

/**
* @author s1mple
* @description 针对表【samples】的数据库操作Service
* @createDate 2023-02-26 00:20:22
*/
public interface SamplesService extends IService<Samples> {

    ResponseResult searchLefse(String name, Integer currentPage,Integer pageSize);

    ResponseResult searchMicrobial(String name, Integer currentPage, Integer pageSize);

     ResponseResult searchSample(SearchFormDTO searchFormDTO);

//    ResponseResult countSample(String bioSample);
}
