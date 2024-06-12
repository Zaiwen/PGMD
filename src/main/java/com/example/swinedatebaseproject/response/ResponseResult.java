package com.example.swinedatebaseproject.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * &#064;Author
 * @Date  2022/11/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//public class ResponseResult<T> implements Serializable {
//    private String code;  // 使用字符串类型的code
//    private String message;  // 使用字符串类型的message
//    private T data;
//
//
//    public static ResponseResult success() {
//        return new ResponseResult(ResponseResultCode.SUCCESS.getCode(), ResponseResultCode.SUCCESS.getMessage(),null);
//    }
//
//    public static ResponseResult success(Object data) {
//        return new ResponseResult(ResponseResultCode.SUCCESS.getCode(), ResponseResultCode.SUCCESS.getMessage(),data);
//    }
//
//    public static ResponseResult success(String code,String message) {
//        return new ResponseResult(code,message,null);
//    }
//
//    public static ResponseResult success(String code, String message,Object data) {
//        return new ResponseResult(code,message,data);
//    }
//
//    public static ResponseResult error(String code,String message) {
//        return new ResponseResult(code,message,null);
//    }
//}
public class ResponseResult<T> implements Serializable {
    private String code;  // 使用字符串类型的code
    private String message;  // 使用字符串类型的message
    private T data;

    // 其他字段和构造函数保持不变

    // 构造函数，接受字符串类型的code和message
    public ResponseResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // 静态方法，兼容第二个类的方法
    public static ResponseResult success() {
        return new ResponseResult("200", "Success", null);
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult("200", "Success", data);
    }

    public static ResponseResult success(String code, String message) {
        return new ResponseResult(code, message, null);
    }

    public static ResponseResult success(String code, String message, Object data) {
        return new ResponseResult(code, message, data);
    }

    public static ResponseResult error(String code, String message) {
        return new ResponseResult(code, message, null);
    }


    public static ResponseResult success(int code, String message) {
        return new ResponseResult(String.valueOf(code), message, null);
    }

    public static ResponseResult error(int code, String message) {
        return new ResponseResult(String.valueOf(code), message, null);
    }
}
