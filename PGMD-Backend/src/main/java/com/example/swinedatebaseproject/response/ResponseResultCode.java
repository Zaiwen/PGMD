package com.example.swinedatebaseproject.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author 132
 * @Date  2022/11/14
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
public enum ResponseResultCode {
    SUCCESS("200", "请求成功"),
    DATA_NOT_FOUND("404", "目标数据未找到"),
    DELETE_SUCCESS("200", "数据删除成功"),
    DELETE_FAIL("400", "数据删除失败"),
    ADD_SUCESS("200", "数据添加成功"),
    ADD_FAIL("400", "数据添加失败"),
    FILE_TYPE_ERROR("400", "文件类型错误"),
    FILE_NULL("400", "没有上传文件"),
    DATA_TYPE_ERROR("400", "数据类型错误"),
    UPDATE_DATA_FAIL("400", "数据更新失败"),
    LOGIN_SUCCESS("200", "登录成功"),
    LOGIN_FAIL("400", "用户名或密码错误"),
    REGISTER_SUCCESS("200", "注册成功"),
    REGISTER_FAIL("400", "注册失败"),
    NAME_EXIST("400", "用户名已存在"),
    EMAIL_EXIST("400", "邮箱已存在"),
    EMAIL_INVALID("400", "邮箱格式不正确"),
    CODE_NOT_FOUND("400", "验证码未找到"),
    CODE_MISMATCH("400", "验证码不匹配"),
    PASSWORD_NOT_STANDARDIZED("400", "密码不符合规范"),


    CODE_NULL("400", "验证码失效"),
    NOT_REGISTER("400", "未注册"),
    NOT_LOGIN("401", "未登录"),
    AUTHEMTICATION_FAIL("401", "认证失败，请重新登录"),

    ID_ERROR("500","ID不对");




    private final String code;
    private final String message;
}

