package com.example.swinedatebaseproject.exception;

/**
 * @Author 刘铭康
 * @Date  2022/11/14
 */
public class TargetDateNotFoundException extends RuntimeException {
    public TargetDateNotFoundException() {
        super();
    }

    public TargetDateNotFoundException(String message) {
        super(message);
    }
}
