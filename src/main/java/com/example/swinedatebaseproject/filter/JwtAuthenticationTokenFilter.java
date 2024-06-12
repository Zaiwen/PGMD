package com.example.swinedatebaseproject.filter;

import com.alibaba.fastjson.JSON;
import com.example.swinedatebaseproject.domain.LoginUser;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.response.ResponseResultCode;
import com.example.swinedatebaseproject.util.JwtUtils;
import com.example.swinedatebaseproject.util.RedisCache;
import com.example.swinedatebaseproject.util.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("Authorization");
        if(!StringUtils.hasText(token)){
            //说明该接口不需要登录  直接放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析获取userid
        Claims claims = null;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时  token非法
            //响应告诉前端需要重新登录
            ResponseResult responseResult = ResponseResult.error(ResponseResultCode.AUTHEMTICATION_FAIL.getCode(), ResponseResultCode.AUTHEMTICATION_FAIL.getMessage());
            WebUtils.renderString(response, JSON.toJSONString(responseResult));
            return;
        }
        String userName = (String) claims.get("userName");
        //从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userName);
        //如果获取不到
        if(Objects.isNull(loginUser)){
            //说明登录过期  提示重新登录
            ResponseResult responseResult = ResponseResult.error(ResponseResultCode.AUTHEMTICATION_FAIL.getCode(), ResponseResultCode.AUTHEMTICATION_FAIL.getMessage());
            WebUtils.renderString(response, JSON.toJSONString(responseResult));
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }


}