package com.example.swinedatebaseproject.domain;

// 自定义验证码数据结构，包括验证码和失效时间
public  class CodeData {
    private final String code;
    private final long expirationTimeMillis;

    public CodeData(String code, long expirationTimeMillis) {
        this.code = code;
        this.expirationTimeMillis = expirationTimeMillis;
    }

    public String getCode() {
        return code;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTimeMillis;
    }
}