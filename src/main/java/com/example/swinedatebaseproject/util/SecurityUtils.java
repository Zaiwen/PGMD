package com.example.swinedatebaseproject.util;

import com.example.swinedatebaseproject.constant.HttpStatus;
import com.example.swinedatebaseproject.domain.LoginUser;
import com.example.swinedatebaseproject.exception.ServiceException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

/**
 * 安全服务工具类
 * 
 * @author feidian
 */
public class SecurityUtils
{

    
    /**
     * 获取用户账户
     **/
    public static String getUsername()
    {
        try
        {
            return getLoginUser().getUser().getName();
        }
        catch (Exception e)
        {
            throw new ServiceException("获取用户账户异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        try
        {
            return (LoginUser) getAuthentication().getPrincipal();
        }
        catch (Exception e)
        {
            throw new ServiceException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }




}