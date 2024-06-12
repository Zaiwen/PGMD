package com.example.swinedatebaseproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 刘铭康
 * @Date 2022/11/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonUserDto {
    private String name;
    private String mail;
}
