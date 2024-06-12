package com.example.swinedatebaseproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("/com/example/swinedatebaseproject/mapper")
public class SwineDateBaseProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwineDateBaseProjectApplication.class, args);
    }

}
