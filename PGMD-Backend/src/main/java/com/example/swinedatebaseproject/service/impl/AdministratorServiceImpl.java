package com.example.swinedatebaseproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swinedatebaseproject.cache.UserCache;
import com.example.swinedatebaseproject.domain.Administrator;
import com.example.swinedatebaseproject.mapper.AdministratorMapper;
import com.example.swinedatebaseproject.service.AdministratorService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
* @author s1mple
* @description 针对表【administrator】的数据库操作Service实现
* @createDate 2022-12-01 19:25:27
*/
@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator>
    implements AdministratorService{

    @PostConstruct
    public void fillData() {
        List<String> list = list().stream().map(Administrator::getName).toList();
        UserCache.ADMINISTRATORS.addAll(list);
    }
}




