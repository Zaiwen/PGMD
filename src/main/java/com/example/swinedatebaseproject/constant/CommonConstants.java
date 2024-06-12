package com.example.swinedatebaseproject.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 刘铭康
 * @Date  2022/11/14
 */
public class CommonConstants {
    public static final String SUFFIX = "List";

//    public static final String WINDOW_FILE_PATH_TEST = "C:\\Users\\s1mple\\Desktop\\";
    public static final String WINDOW_FILE_PATH_TEST = "C:\\Users\\space dandy\\Desktop\\file\\1\\";

//    public static final String WINDOW_FILE_PATH = "E:";
//    public static final String LINUX_FILE_PATH = "/tmp/";
    public static final String LINUX_FILE_PATH = "/root/feidian/tmp/";


    public static final List<String> FILE_TYPES = new ArrayList<>() {
        {
            add(".csv");
            add(".xls");
            add(".xlsx");
        }
    };
//    public static final List<Class<?>> PACKING_TYPES = new ArrayList<>() {
//        {
//            add(String.class);
//            add(Long.class);
//            add(Integer.class);
//        }
//    };
    public static final String INTEGER = "INTEGER";

    public static final String LONG = "LONG";

    public static final String STRING = "String";

    public static final String SEPARATOR = ".";

    public static final String INTEGER_CLASS = "java.lang.Integer";

    public static final String LONG_CLASS = "java.lang.Integer";

    public static final String STRING_CLASS = "java.lang.Integer";

    public static final String metaDataFilePath = "/root/feidian/file/metaDataFile";
    public static final String classificationFilePath = "/root/feidian/file/classificationFile";
    public static final String lefseFilePath = "/root/feidian/file/lefseFile";
    public static final String picrust2FilePath = "/root/feidian/file/picrust2File";

}
