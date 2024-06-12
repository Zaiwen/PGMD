package com.example.swinedatebaseproject.config;

import com.example.swinedatebaseproject.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.Filter;

/**
 * @Author 123
 * @Date 2022/11/14
 */

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.setOrder(Integer.MAX_VALUE);
        return registrationBean;
    }

//  未启用
//    @Bean
//    public FilterRegistrationBean dynamicDatasourceFilter() {
//        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new DynamicDatasourceFilter());
//        registrationBean.setOrder(-1);
//        return registrationBean;
//    }
}
