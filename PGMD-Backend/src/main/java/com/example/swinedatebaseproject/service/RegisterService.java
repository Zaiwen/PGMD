package com.example.swinedatebaseproject.service;

import com.example.swinedatebaseproject.domain.Administrator;
import com.example.swinedatebaseproject.domain.CommonUser;
import com.example.swinedatebaseproject.domain.dto.RegisterDTO;
import com.example.swinedatebaseproject.response.ResponseResult;

import javax.servlet.http.HttpSession;

/**
 * @Author 刘铭康
 * @Date 2022/11/14
 */
public interface RegisterService {
    ResponseResult userRegister(RegisterDTO commonUser);

    boolean administratorRegister(Administrator administrator);

    ResponseResult sendEmail(String email, HttpSession session);
}
