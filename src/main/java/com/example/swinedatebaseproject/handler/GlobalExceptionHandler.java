package com.example.swinedatebaseproject.handler;

import com.example.swinedatebaseproject.exception.GenerateException;
import com.example.swinedatebaseproject.exception.TargetDateNotFoundException;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.response.ResponseResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author 123
 * @Date  2022/11/14
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({TargetDateNotFoundException.class})
    public ResponseResult handleException(TargetDateNotFoundException e) {
        return ResponseResult.error(ResponseResultCode.DATA_NOT_FOUND.getCode(), ResponseResultCode.DATA_NOT_FOUND.getMessage());
    }

    @ExceptionHandler({GenerateException.class})
    public ResponseResult handleGenerateException(GenerateException e) {
        log.error(e.getMessage());
        return ResponseResult.error(ResponseResultCode.ADD_FAIL.getCode(), e.getMessage());
    }

}
