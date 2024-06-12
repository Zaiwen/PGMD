package com.example.swinedatebaseproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 刘铭康
 * @Date 2022/11/14
 */
@Configuration
public class SpringDocConfig {
    @Bean
    @SuppressWarnings("all")
    public OpenAPI mallTinyOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SwineSearchProject")
                        .description("生猪数据库后端协同系统")
                        .version("1.0.0")
                        .license(new License().
                                name("Apache 2.0").
                                url("https://gitee.com/Liu_DingDing/swine-search-project/blob/master/LICENSE")));
    }

}
