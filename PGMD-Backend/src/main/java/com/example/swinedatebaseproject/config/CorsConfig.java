package com.example.swinedatebaseproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 跨域资源共享（CORS）配置类。
 * 该类实现了 WebMvcConfigurer 接口，并重写了 addCorsMappings 方法来定义 CORS 配置。
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 重写 addCorsMappings 方法以配置 CORS 映射。
     *
     * @param registry CorsRegistry 对象，用于定义 CORS 映射。
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true) // 允许跨域请求使用凭证（例如：cookies、授权头）。
                .allowedOriginPatterns("*") // 允许来自任意来源的请求。
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许指定的 HTTP 方法用于 CORS 请求。
                .allowedHeaders("*") // 允许跨域请求中的任意头信息。
                .exposedHeaders("*"); // 将 CORS 响应中的任意头信息暴露给客户端。
    }
}