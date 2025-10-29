package com.example.swinedatebaseproject.converter;

import com.example.swinedatebaseproject.domain.CommonUser;
import com.example.swinedatebaseproject.domain.CommonUserDto;

/**
 * @Author 刘铭康
 * @Date 2022/11/14
 */
public class CommonUserConverter {
    public static CommonUserDto ConvertCommonUserDtoFromCommonUser(CommonUser commonUser) {
        return new CommonUserDto(commonUser.getName(), commonUser.getMail());
    }
}
