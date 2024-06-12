package com.example.swinedatebaseproject.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @Author dd
 * @Date 2022/11/14
 */
//未使用
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public CustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }


    @Override
    public String getParameter(String name) {
        return super.getParameter(name);
    }
}
