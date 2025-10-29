package com.example.swinedatebaseproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.swinedatebaseproject.domain.Microbial;
import com.example.swinedatebaseproject.response.ResponseResult;

/**
* @author s1mple
* @description 针对表【cds】的数据库操作Service
* @createDate 2023-02-26 00:20:22
*/
public interface MicrobialService extends IService<Microbial> {

    ResponseResult countLevelByName();

    ResponseResult searchMicrobial(String name);

    ResponseResult countGroup(String name);

    ResponseResult searchMicrobialByProject(String name);
}
