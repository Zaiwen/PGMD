package com.example.swinedatebaseproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.swinedatebaseproject.domain.CommonUser;
import com.example.swinedatebaseproject.domain.LoginUser;
import com.example.swinedatebaseproject.mapper.CommonUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private CommonUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<CommonUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CommonUser::getName,username);
        CommonUser user=userMapper.selectOne(queryWrapper);
        //判断是否查看用户  如果没查到抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        LoginUser loginUser =new LoginUser();
        loginUser.setUser(user);
        return loginUser;

    }
}