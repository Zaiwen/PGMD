package com.example.swinedatebaseproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swinedatebaseproject.cache.UserCache;
import com.example.swinedatebaseproject.domain.CommonUser;
import com.example.swinedatebaseproject.mapper.CommonUserMapper;
import com.example.swinedatebaseproject.service.CommonUserService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
* @author s1mple
* @description 针对表【common_user】的数据库操作Service实现
* @createDate 2022-12-01 19:25:27
*/
@Service
public class CommonUserServiceImpl extends ServiceImpl<CommonUserMapper, CommonUser>
    implements CommonUserService{

    @PostConstruct
    public void fillData() {
        List<String> list = list().stream().map(CommonUser::getName).toList();
        UserCache.COMMON_USERS.addAll(list);
    }
}




