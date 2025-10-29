package com.example.swinedatebaseproject.util;

import com.example.swinedatebaseproject.exception.TargetDateNotFoundException;

import java.util.Objects;

/**
 * @Author 123
 * @Date  2022/11/14
 */
public class ObjectUtils {
    public static void nonNullAndThrowTargetDateNotFoundException(Object data) {
        if (Objects.isNull(data)) {
            throw new TargetDateNotFoundException();
        }
    }
}
