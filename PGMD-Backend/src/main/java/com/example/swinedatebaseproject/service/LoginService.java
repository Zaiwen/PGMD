package com.example.swinedatebaseproject.service;

import com.example.swinedatebaseproject.domain.Administrator;
import com.example.swinedatebaseproject.domain.CommonUser;
import com.example.swinedatebaseproject.domain.dto.LoginUserDTO;
import com.example.swinedatebaseproject.response.ResponseResult;

/**
 * @Author 刘铭康
 * @Date 2022/11/14
 */
public interface LoginService {
    ResponseResult userLogin(LoginUserDTO commonUser);

    boolean administratorLogin(Administrator administrator);
}
