package com.example.swinedatebaseproject.controller.user;

import com.example.swinedatebaseproject.domain.Administrator;

import com.example.swinedatebaseproject.domain.CommonUser;
import com.example.swinedatebaseproject.domain.LoginUser;
import com.example.swinedatebaseproject.domain.dto.LoginUserDTO;
import com.example.swinedatebaseproject.service.LoginService;
import com.example.swinedatebaseproject.util.JwtUtils;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.response.ResponseResultCode;
import com.example.swinedatebaseproject.util.RedisCache;
import com.example.swinedatebaseproject.util.ResponseResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @Author 123
 * @Date 2022/11/14
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    private RedisCache redisCache;

    @PostMapping(value = "/user")
    // 用户登录接口，验证 CommonUser 对象的凭据是否有效
    public ResponseResult userLogin(@RequestBody LoginUserDTO commonUser) {
        return loginService.userLogin(commonUser);
    }

    @PostMapping(value = "/administrator")
    // 管理员登录接口，验证 Administrator 对象的凭据是否有效
    public ResponseResult administratorLogin(@RequestBody Administrator administrator) {
        if (loginService.administratorLogin(administrator)) {
            // 生成管理员令牌
            String jwt = JwtUtils.createJwt("Login", Map.of("userName", administrator.getName()));
            // 把用户信息存到redis
            LoginUser loginUser =new LoginUser();
            CommonUser user =new CommonUser(administrator.getName(),administrator.getPassword(),administrator.getMail());
            loginUser.setUser(user);
            loginUser.setToken(jwt);

            redisCache.setCacheObject("login:"+administrator.getName(),loginUser);
            // 返回登录成功的响应结果，附带管理员令牌
            return ResponseResultUtils.getResponseResult(ResponseResultCode.LOGIN_SUCCESS.getCode(), ResponseResultCode.LOGIN_SUCCESS.getMessage(), "token", jwt);
        } else {
            // 返回登录失败的响应结果
            return ResponseResult.error(ResponseResultCode.LOGIN_FAIL.getCode(), ResponseResultCode.LOGIN_FAIL.getMessage());
        }
    }

}
