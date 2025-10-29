package com.example.swinedatebaseproject.mapper;

import com.example.swinedatebaseproject.domain.CommonUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.swinedatebaseproject.domain.dto.RegisterDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author s1mple
* @description 针对表【common_user】的数据库操作Mapper
* @createDate 2022-12-01 19:25:27
* @Entity com.example.swinedatebaseproject.domain.CommonUser
*/
@Mapper
public interface CommonUserMapper extends BaseMapper<CommonUser> {

    // 检查邮箱是否存在，返回boolean
    boolean isEmailExist(@Param("mail") String mail);

    boolean authenticateUser(@Param("name") String name, @Param("password") String password);

    void insertUser(RegisterDTO commonUser);

    boolean isNameExits(@Param("name") String name);
}




