package com.example.swinedatebaseproject.service.impl;

import com.example.swinedatebaseproject.cache.UserCache;
import com.example.swinedatebaseproject.domain.Administrator;
import com.example.swinedatebaseproject.domain.CodeData;
import com.example.swinedatebaseproject.domain.CommonUser;
import com.example.swinedatebaseproject.domain.LoginUser;
import com.example.swinedatebaseproject.domain.dto.LoginUserDTO;
import com.example.swinedatebaseproject.mapper.AdministratorMapper;
import com.example.swinedatebaseproject.mapper.CommonUserMapper;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.response.ResponseResultCode;
import com.example.swinedatebaseproject.service.AdministratorService;
import com.example.swinedatebaseproject.service.CommonUserService;
import com.example.swinedatebaseproject.util.JwtUtils;
import com.example.swinedatebaseproject.util.RedisCache;
import com.example.swinedatebaseproject.util.ResponseResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author 刘铭康
 * @Date 2022/11/14
 */
@Service
public class LoginServiceImpl implements com.example.swinedatebaseproject.service.LoginService {

    @Autowired
    CommonUserService commonUserService;

    @Autowired
    AdministratorService administratorService;

    @Autowired
    CommonUserMapper commonUserMapper;

    @Autowired
    AdministratorMapper administratorMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @PostConstruct
    public void initUserCache() {
        List<CommonUser> commonUsers = commonUserService.list();
        for (CommonUser commonUser : commonUsers) {
            UserCache.COMMON_USERS.add(commonUser.getName());
        }

        List<Administrator> administratorUsers = administratorService.list();
        for (Administrator administratorUser:
                administratorUsers) {
            UserCache.ADMINISTRATORS.add(administratorUser.getName());

        }
    }

    @Override
    public ResponseResult userLogin(LoginUserDTO commonUser) {
        // 获取用户输入的密码
        String password = commonUser.getPassword();

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(commonUser.getName(), password);
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            // 如果认证成功，您可以继续处理其他逻辑
            // 获取登录用户信息
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();


            // 生成用户令牌
            String jwt = JwtUtils.createJwt("Login", Map.of("userName", commonUser.getName()));

            loginUser.setToken(jwt);

            // 把用户信息存入redis
            redisCache.setCacheObject("login:"+commonUser.getName(),loginUser);

            // 返回登录成功的响应
            return ResponseResult.success(ResponseResultCode.LOGIN_SUCCESS.getCode(), ResponseResultCode.LOGIN_SUCCESS.getMessage(), loginUser);
        } catch (AuthenticationException e) {
            // 认证失败，您可以在此处处理认证失败的情况
            return ResponseResult.error(ResponseResultCode.LOGIN_FAIL.getCode(), ResponseResultCode.LOGIN_FAIL.getMessage());
        }

    }


    @Override
    public boolean administratorLogin(Administrator administrator) {
        return administratorMapper.authenticate(administrator.getName(),administrator.getPassword());
    }

}
