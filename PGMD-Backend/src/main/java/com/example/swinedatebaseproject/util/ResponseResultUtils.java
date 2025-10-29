package com.example.swinedatebaseproject.util;

import com.example.swinedatebaseproject.response.ResponseResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 123
 * @Date  2022/11/14
 * @Description 向ResponseResult中增加单条数据（单对象/单集合）
 */
public class ResponseResultUtils {

    private static String getCommonKeyName(String simpleName) {
        String oldFirstChar = String.valueOf(simpleName.charAt(0));
        String lowerCaseClassName = simpleName.toLowerCase();
        String newFirstChar = String.valueOf(lowerCaseClassName.charAt(0));
        return simpleName.replaceFirst(oldFirstChar, newFirstChar);
    }

    private static <T> String getKeyNameBaseSingleData(Class<T> dataClass) {
        return getCommonKeyName(dataClass.getSimpleName());
    }


    private static <T> String getKeyNameBaseCollectionData(Class<T> dataClass) {

//        return getCommonKeyName(dataClass.getSimpleName()) + CommonConstants.SUFFIX; //这一行代码没问题，只是迎合前端去修改
        return getCommonKeyName(dataClass.getSimpleName());
    }

    private static <T> Map<String, Object> getDataMap(Class<T> dataClass, Object data) {
        ObjectUtils.nonNullAndThrowTargetDateNotFoundException(data);
        String keyName = data instanceof Collection ? getKeyNameBaseCollectionData(dataClass) : getKeyNameBaseSingleData(dataClass);

        return new HashMap<>() {
            {
//                put(keyName, data);  //这一行代码没问题，只是迎合前端去修改
                put("recordList", data);
            }
        };
    }

    /**
     *  keyName 需基于传入数据动态指定
     *      单个Swine对象 keyName->swine
     *      Swine对象集合 keyName->swineList
     */
    public static <T> ResponseResult getResponseResult(Class<T> dataClass, Object data) {

        Map<String, Object> dataMap = getDataMap(dataClass, data);
        return ResponseResult.success(dataMap);
    }

    /**
     *  自指定keyName
     */
    public static ResponseResult getSuccessResponseResult(String keyName, Object data) {
        Map<String, Object> dataMap = new HashMap<>() {
            {
                put(keyName, data);
            }
        };
        return ResponseResult.success(dataMap);
    }

    /**
     *  自指定keyName
     */
    public static ResponseResult getResponseResult(String message,String code,String keyName, Object data) {
        Map<String, Object> dataMap = new HashMap<>() {
            {
                put(keyName, data);
            }
        };
        return ResponseResult.success(message,code,dataMap);
    }


}
