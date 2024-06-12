package com.example.swinedatebaseproject.filter;

import com.example.swinedatebaseproject.cache.UserCache;
import com.example.swinedatebaseproject.domain.CodeData;
import com.example.swinedatebaseproject.domain.LoginUser;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.response.ResponseResultCode;
import com.example.swinedatebaseproject.util.JwtUtils;
import com.example.swinedatebaseproject.util.RedisCache;
import com.example.swinedatebaseproject.util.WebUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @Author 123
 * @Date 2022/11/14
 */
@CrossOrigin
@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERNAME = "userName";

    private static final String LOGIN_PREFIX = "/login";

    private static final String REGISTER_PREFIX = "/register";

    // todo
    public static final String  UPLOAD ="/Project/upload";
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求的 URL
        String requestURI = request.getRequestURI();

        // 判断请求的 URL 是否是需要验证的路径
        if (UPLOAD.equals(requestURI)) {
            // 从请求头中获取 JWT 令牌
            String headerValue = request.getHeader("Authorization");

            // 如果令牌为空，则返回认证失败的错误信息
            if (!StringUtils.hasText(headerValue)) {
                ResponseResult responseResult = ResponseResult.error(ResponseResultCode.AUTHEMTICATION_FAIL.getCode(), ResponseResultCode.AUTHEMTICATION_FAIL.getMessage());
                String value = objectMapper.writeValueAsString(responseResult);
                WebUtils.renderString(response, value);
                return;
            }

            // 解析 JWT 令牌，获取用户名
            Claims claims;
            try {
                claims = JwtUtils.parseJWT(headerValue);
            } catch (Exception e) {
                // JWT 令牌解析失败，返回认证失败的错误信息
                ResponseResult responseResult = ResponseResult.error(ResponseResultCode.AUTHEMTICATION_FAIL.getCode(), ResponseResultCode.AUTHEMTICATION_FAIL.getMessage());
                String value = objectMapper.writeValueAsString(responseResult);
                WebUtils.renderString(response, value);
                return;
            }

            String userName = (String) claims.get(USERNAME);

            // 如果用户名为空，则返回认证失败的错误信息
            if (!StringUtils.hasText(userName)) {
                ResponseResult responseResult = ResponseResult.error(ResponseResultCode.AUTHEMTICATION_FAIL.getCode(), ResponseResultCode.AUTHEMTICATION_FAIL.getMessage());
                String value = objectMapper.writeValueAsString(responseResult);
                WebUtils.renderString(response, value);
                return;
            }

            //从redis中获取用户信息
            LoginUser loginUser = redisCache.getCacheObject("login:" + userName);
            //如果获取不到
            if(Objects.isNull(loginUser)){
                ResponseResult responseResult = ResponseResult.error(ResponseResultCode.AUTHEMTICATION_FAIL.getCode(), ResponseResultCode.AUTHEMTICATION_FAIL.getMessage());
                String value = objectMapper.writeValueAsString(responseResult);
                WebUtils.renderString(response, value);
                return;
            }

            //存入SecurityContextHolder
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // 执行过滤器链
        filterChain.doFilter(request, response);
    }

}
