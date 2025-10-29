package com.example.swinedatebaseproject.config;

import com.example.swinedatebaseproject.interceptor.SearchMultiKeyHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebAppConfigurer implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SearchMultiKeyHandlerInterceptor()).addPathPatterns("/**");

//        registry.addInterceptor(new SearchMultiKeyHandlerInterceptor()).addPathPatterns("/*/search-multi-key");
    }
}
