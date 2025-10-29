package com.example.swinedatebaseproject.controller.data;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.swinedatebaseproject.constant.CommonConstants;
import com.example.swinedatebaseproject.constant.DomainUnit;
import com.example.swinedatebaseproject.constant.MyBatisConstants;
import com.example.swinedatebaseproject.listener.CommonReadListener;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.response.ResponseResultCode;
import com.example.swinedatebaseproject.util.ResponseResultUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 123
 * @Date 2022/11/14
 */
@Slf4j
@SuppressWarnings("DuplicatedCode")
public abstract class CommonController<T> {

     public IService service;
     public Class<?> thisClass;
     public String queryColumnName;
    public List<String> fieldNames;

    public CommonController(String queryColumnName) {
        String simpleName = this.getClass().getSimpleName();
        String className = simpleName.replace("Controller", "");
        try {
            if(Objects.isNull(thisClass = DomainUnit.NAME_CLASS_MAP.get(className))){
                thisClass = Class.forName("com.example.swinedatebaseproject.domain." + className);
                DomainUnit.NAME_CLASS_MAP.put(className, this.thisClass);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.queryColumnName = queryColumnName;
        fieldNames = new ArrayList<>();
        fieldNamesInit();
    }

    /**
     * 填充fieldNames
     */
    private void fieldNamesInit() {
        Field[] fields = thisClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!fieldName.contains("serialVersion")) {
                fieldNames.add(fieldName);
            }
        }
//        fieldNames.stream().forEach(System.out::println);
    }

    public abstract ResponseResult getFieldNames();

    public ResponseResult getFieldNamesActual() {
        return ResponseResultUtils.getSuccessResponseResult("fieldNames",fieldNames);
    }

    public abstract ResponseResult getSingleDataById(Integer id);

    public ResponseResult getSingleDataByIdActual(Integer id) {
        Object o = service.getById(id);
        return ResponseResultUtils.getSuccessResponseResult("recordList" ,o);
    }

    public abstract ResponseResult getAllData();

    public ResponseResult getAllDataActual() {
        List<T> list = service.list();
        return ResponseResultUtils.getSuccessResponseResult("recordList", list);
    }

    public abstract ResponseResult getDataByPage(Integer current);

    public ResponseResult getDataByPageActual(Integer current) {
        Page<T> page = new Page<>(current, MyBatisConstants.PAGE_SIZE);
        List<T> records = service.page(page).getRecords();
        return ResponseResultUtils.getSuccessResponseResult("recordList", records);
    }

    public abstract ResponseResult getPageCount();

    public ResponseResult getPageCountActual() {
        Long totalRows = service.count();
        Long pageCount = totalRows % MyBatisConstants.PAGE_SIZE == 0 ? totalRows / MyBatisConstants.PAGE_SIZE : totalRows / MyBatisConstants.PAGE_SIZE + 1;
        return ResponseResultUtils.getSuccessResponseResult("pageCount", pageCount);
    }

    public abstract ResponseResult deleteByIds(List<String> ids);

    public ResponseResult deleteByIdsActual(List<String> ids) {

        if (service.removeByIds(ids)) {
            return ResponseResult.success(ResponseResultCode.DELETE_SUCCESS.getCode(), ResponseResultCode.DELETE_SUCCESS.getMessage());
        } else {
            return ResponseResult.success(ResponseResultCode.DELETE_FAIL.getCode(), ResponseResultCode.DELETE_FAIL.getMessage());
        }
    }

    public abstract ResponseResult getRowsSize();

    public ResponseResult getRowsSizeActual() {
        Long totalRows = service.count();
        return ResponseResultUtils.getSuccessResponseResult("rowsSize", totalRows);
    }
//    模糊查询
    public abstract ResponseResult getData(String name,Integer currentPage);

    public ResponseResult getDataActual(String name,Integer currentPage) {
        if (currentPage <= 0) {
            return ResponseResult.success();
        }

        Page<T> page = new Page<>(currentPage, MyBatisConstants.PAGE_SIZE);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(queryColumnName, name);
        service.page(page, queryWrapper);
        List<T> list = page.getRecords();
        long rowCount = page.getTotal();
        long pageSize = page.getPages();

        if (list.size() == 0) {
            return ResponseResult.error(ResponseResultCode.DATA_NOT_FOUND.getCode(), ResponseResultCode.DATA_NOT_FOUND.getMessage());
        } else {
            HashMap<String, Object> map = new HashMap<>() {
                {
                    put("recordList", list);
                    put("rowCount", rowCount);
                    put("pageSize", pageSize);
                }
            };
            return ResponseResult.success(map);
        }
    }

//精确查询
    public abstract ResponseResult getData_precise(String name,Integer currentPage);
    public ResponseResult getDataActual_precise(String name,Integer currentPage)
    {
        if (currentPage <= 0) {
            return ResponseResult.success();
        }

        Page<T> page = new Page<>(currentPage, MyBatisConstants.PAGE_SIZE);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(queryColumnName, name);
        service.page(page, queryWrapper);
        List<T> list = page.getRecords();
        long rowCount = page.getTotal();
        long pageSize = page.getPages();

        if (list.size() == 0) {
            return ResponseResult.error(ResponseResultCode.DATA_NOT_FOUND.getCode(), ResponseResultCode.DATA_NOT_FOUND.getMessage());
        } else {
            HashMap<String, Object> map = new HashMap<>() {
                {
                    put("recordList", list);
                    put("rowCount", rowCount);
                    put("pageSize", pageSize);
                }
            };
            return ResponseResult.success(map);
        }
    }


    public abstract ResponseResult upload(MultipartFile multipartFile);

    /**
     * 上传文件并保存的方法。
     *
     * @param multipartFile 要上传的文件
     * @return ResponseResult 包含上传结果的响应
     */
    public ResponseResult uploadActual(MultipartFile multipartFile) {
        // 获取操作系统名称
        String systemProperty = System.getProperty("os.name");

        // 生成唯一的文件名后缀
        long fileSuffix = System.currentTimeMillis();

        // 获取原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.indexOf(".");
        // 获取文件类型后缀
        String fileType = originalFilename.substring(i);
        // 构建新的文件名，包含时间戳
        String fileName = originalFilename.substring(0, i) + "_" + fileSuffix + fileType;

        // 检查文件类型是否在允许的文件类型列表中
        if (Boolean.FALSE.equals(CommonConstants.FILE_TYPES.contains(fileType))) {
            return ResponseResult.error(ResponseResultCode.FILE_TYPE_ERROR.getCode(), ResponseResultCode.FILE_TYPE_ERROR.getMessage());
        }

        // 根据操作系统类型选择不同的文件保存路径
        if (systemProperty.toUpperCase().contains("WINDOW")) {
            File file = new File(CommonConstants.WINDOW_FILE_PATH_TEST + fileName);
            return saveData(multipartFile, file);
        } else if (systemProperty.toUpperCase().contains("LINUX")) {
            File file = new File(CommonConstants.LINUX_FILE_PATH + fileName);
            return saveData(multipartFile, file);
        } else {
            // 未识别的操作系统类型
            return ResponseResult.error(ResponseResultCode.ADD_FAIL.getCode(), ResponseResultCode.ADD_FAIL.getMessage());
        }
    }


    public ResponseResult saveData(MultipartFile multipartFile, File file) {
        try {
            multipartFile.transferTo(file);
            file.createNewFile();
            ExcelReader excelReader =EasyExcel.read(file, thisClass, new CommonReadListener<T>(service))
                    .build();
            excelReader.readAll();
            excelReader.finish();
            return ResponseResult.success(ResponseResultCode.ADD_SUCESS.getCode(), ResponseResultCode.ADD_SUCESS.getMessage());
        } catch (IOException e) {
            return ResponseResult.error(ResponseResultCode.ADD_FAIL.getCode(), ResponseResultCode.ADD_FAIL.getMessage());
        }
    }


    public abstract ResponseResult searchValueByMultiKey(List<String> values, Integer currentPage);

    public ResponseResult searchValueByMultiKeyActual(List<String> values, Integer currentPage){

        if (currentPage <= 0) {
            return ResponseResult.success();
        }

        HashMap<String, String> hashMap = getFiledValueMap(values);

        Page<T> page = new Page<>(currentPage, MyBatisConstants.PAGE_SIZE);

        QueryWrapper<T> wrapper = new QueryWrapper<>();
        NullValueFlag nullValueFlag = new NullValueFlag();
        ResponseResult responseResult = fillQueryWrapperWhenSearchingData(hashMap, wrapper, nullValueFlag);
        if (responseResult != null) {
            return responseResult;
        }

        HashMap<String, Object> map;
        if (nullValueFlag.getCount() > 0) {
            List<T> records = service.page(page, wrapper).getRecords();
            long rowCount = service.count(wrapper);
            long pageSize = rowCount % MyBatisConstants.PAGE_SIZE == 0 ? rowCount / MyBatisConstants.PAGE_SIZE + 1 : rowCount / MyBatisConstants.PAGE_SIZE;
            map = new HashMap<>() {
                {
                    put("recordList", records);
                    put("rowCount", rowCount);
                    put("pageSize", pageSize);
                }
            };
        }else{
            map = new HashMap<>() {
                {
                    put("recordList", null);
                    put("rowCount", 0);
                    put("pageSize", 1);
                }
            };
        }
        return ResponseResult.success(map);

    }

    public abstract ResponseResult saveSingleData(T object);

    public ResponseResult saveSingleDataActual(T object) {
        try {
            if (service.save(object)) {
                return ResponseResult.success();
            }else{
                return ResponseResult.error(ResponseResultCode.ADD_FAIL.getCode(), "添加数据失败，可能存在主键冲突或类型匹配");
            }
        } catch (Exception e) {
            return ResponseResult.error(ResponseResultCode.ADD_FAIL.getCode(), "添加数据失败，可能存在主键冲突或类型匹配");
        }
    }

    public abstract ResponseResult updateSingleData(List<String> values, T object);

    public ResponseResult updateSingleDataActual(List<String> values, T object) {


        HashMap<String, String> filedValueMap = getFiledValueMap(values);

        UpdateWrapper<T> wrapper = new UpdateWrapper<>();
        ResponseResult responseResult = fillQueryWrapperWhenUpdatingData(filedValueMap, wrapper);
        if (responseResult != null) {
            return responseResult;
        }

        if (service.update(object, wrapper)) {
            return ResponseResult.success(ResponseResultCode.SUCCESS.getCode(),"数据更新成功");
        } else {
            return ResponseResult.error(ResponseResultCode.UPDATE_DATA_FAIL.getCode(), ResponseResultCode.UPDATE_DATA_FAIL.getMessage());
        }

    }

    public abstract ResponseResult deleteSingleDataByColumns(List<String> values);

    public ResponseResult deleteSingleDataByColumnsActual(List<String> values) {

        HashMap<String, String> filedValueMap = getFiledValueMap(values);

        QueryWrapper<T> wrapper = new QueryWrapper<>();
        ResponseResult responseResult = fillQueryWrapperWhenDeletingData(filedValueMap, wrapper);
        if (responseResult != null) {
            return responseResult;
        }

        if (service.remove(wrapper)) {
            return ResponseResult.success(ResponseResultCode.DELETE_SUCCESS.getCode(), ResponseResultCode.DELETE_SUCCESS.getMessage());
        }else{
            return ResponseResult.success(ResponseResultCode.DELETE_FAIL.getCode(), ResponseResultCode.DELETE_FAIL.getMessage());
        }

    }

    public abstract ResponseResult deleteBatchOnMainTable(List<T> values);

    public ResponseResult deleteBatchOnMainTableActual(List<T> values) {
        AtomicInteger deletedRowCount = new AtomicInteger();
        ArrayList<QueryWrapper<T>> wrappers = new ArrayList<>();
        values.forEach(t->{
            QueryWrapper<T> wrapper = new QueryWrapper<>();
            fieldNames.forEach(fieldName->{
                try {
                    Field field = thisClass.getDeclaredField(fieldName);
                    TableId tableId;
                    TableField tableName;
                    String columnName = "";
                    if (Objects.nonNull(tableId= field.getAnnotation(TableId.class))) {
                        columnName = tableId.value();
                    } else if (Objects.nonNull(tableName = field.getAnnotation(TableField.class))) {
                        columnName = tableName.value();
                    }
                    field.setAccessible(true);
                    wrapper.eq(columnName, field.get(t));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.error(e.getMessage());
                }
            });
            wrappers.add(wrapper);
        });

        wrappers.forEach(queryWrapper -> {
            if (service.remove(queryWrapper)) {
                deletedRowCount.getAndIncrement();
            }
        });

        return ResponseResult.success(ResponseResultCode.DELETE_SUCCESS.getCode(), "已成功删除" + deletedRowCount.toString() + "条数据");
    }

    public abstract ResponseResult deleteBatchOnSubTable(List<String> values);

    // 删除
    public ResponseResult deleteBatchOnSubTableActual(List<String> values) {

        HashMap<String, String> filedValueMap = getFiledValueMap(values);

        QueryWrapper<T> wrapper = new QueryWrapper<>();
        NullValueFlag nullValueFlag = new NullValueFlag();
        ResponseResult responseResult = fillQueryWrapperWhenSearchingData(filedValueMap, wrapper, nullValueFlag);
        if (responseResult != null) {
            return responseResult;
        }

        if (nullValueFlag.getCount() > 0) {
            if (service.remove(wrapper)) {
                return ResponseResult.success(ResponseResultCode.DELETE_SUCCESS.getCode(), ResponseResultCode.DELETE_SUCCESS.getMessage());
            } else {
                return ResponseResult.success(ResponseResultCode.DELETE_FAIL.getCode(), ResponseResultCode.DELETE_FAIL.getMessage());
            }
        } else {
            return ResponseResult.success(ResponseResultCode.DELETE_FAIL.getCode(), ResponseResultCode.DELETE_FAIL.getMessage());

        }


    }

    // <-----------------------------------------private method----------------------------------------------------------------------->
    @Nullable
    private ResponseResult fillQueryWrapperWhenSearchingData(HashMap<String, String> fieldAndValueMap, QueryWrapper<T> wrapper, NullValueFlag nullValueFlag) {

        for (Map.Entry<String, String> entry : fieldAndValueMap.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();

            // fieldValue 判空
            if (Objects.isNull(fieldValue) || "".equals(fieldValue) || "null".equals(fieldValue) || "\"\"".equals(fieldValue)) {
                continue;
            }

            nullValueFlag.increment();

            // Java实体字段名->表字段名
            // 检查成员变量是否为Integer类型
            try {
                Field field = thisClass.getDeclaredField(fieldName);
                TableId tableId;
                TableField tableName;
                String columnName = "";
                if (Objects.nonNull(tableId = field.getAnnotation(TableId.class))) {
                    columnName = tableId.value();
                } else if (Objects.nonNull(tableName = field.getAnnotation(TableField.class))) {
                    columnName = tableName.value();
                }
                if ("Integer".equals(field.getType().getSimpleName())) {
                    wrapper.eq(columnName, Integer.valueOf(fieldValue));
                } else {
                    wrapper.like(columnName, fieldValue);
                }
            } catch (NoSuchFieldException e) {
                System.out.println("Field declaredField = thisClass.getDeclaredField(entry.getKey());\n");
                return ResponseResult.error(ResponseResultCode.DATA_TYPE_ERROR.getCode(), ResponseResultCode.DATA_NOT_FOUND.getMessage());
            } catch (NumberFormatException e) {
                return ResponseResult.error(ResponseResultCode.DATA_TYPE_ERROR.getCode(), ResponseResultCode.DATA_NOT_FOUND.getMessage());
            }
        }
        return null;
    }

    @Nullable
    private ResponseResult fillQueryWrapperWhenDeletingData(HashMap<String, String> hashMap, QueryWrapper<T> wrapper) {

        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();

            // fieldValue 判空
            if (Objects.isNull(fieldValue) || "".equals(fieldValue) || "null".equals(fieldValue) || "\"\"".equals(fieldValue)) {
                continue;
            }

            // Java实体字段名->表字段名
            // 检查成员变量是否为Integer类型
            try {
                Field field = thisClass.getDeclaredField(fieldName);
                TableId tableId;
                TableField tableName;
                String columnName = "";
                if (Objects.nonNull(tableId= field.getAnnotation(TableId.class))) {
                    columnName = tableId.value();
                } else if (Objects.nonNull(tableName = field.getAnnotation(TableField.class))) {
                    columnName = tableName.value();
                }
                if ("Integer".equals(field.getType().getSimpleName())) {
                    wrapper.eq(columnName, Integer.valueOf(fieldValue));
                } else {
                    wrapper.eq(columnName, fieldValue);
                }
            } catch (NoSuchFieldException e) {
                System.out.println("Field declaredField = thisClass.getDeclaredField(entry.getKey());\n");
            } catch (NumberFormatException e) {
                return ResponseResult.error(ResponseResultCode.DELETE_FAIL.getCode(), ResponseResultCode.DELETE_FAIL.getMessage());
            }
        }
        return null;
    }

    @Nullable
    private ResponseResult fillQueryWrapperWhenUpdatingData(HashMap<String, String> hashMap, UpdateWrapper<T> wrapper) {
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();

            // fieldValue 判空
            if (Objects.isNull(fieldValue) || "".equals(fieldValue) || "null".equals(fieldValue) || "\"\"".equals(fieldValue)) {
                continue;
            }

            // Java实体字段名->表字段名
            // 检查成员变量是否为Integer类型
            try {
                Field field = thisClass.getDeclaredField(fieldName);
                TableId tableId;
                TableField tableName;
                String columnName = "";
                if (Objects.nonNull(tableId= field.getAnnotation(TableId.class))) {
                    columnName = tableId.value();
                } else if (Objects.nonNull(tableName = field.getAnnotation(TableField.class))) {
                    columnName = tableName.value();
                }
                if ("Integer".equals(field.getType().getSimpleName())) {
                    wrapper.eq(columnName, Integer.valueOf(fieldValue));
                } else {
                    wrapper.eq(columnName, fieldValue);
                }
            } catch (NoSuchFieldException e) {
                System.out.println("Field declaredField = thisClass.getDeclaredField(entry.getKey());\n");
            } catch (NumberFormatException e) {
                return ResponseResult.error(ResponseResultCode.DELETE_FAIL.getCode(), ResponseResultCode.DELETE_FAIL.getMessage());
            }
        }
        return null;
    }

    @NotNull
    private HashMap<String, String> getFiledValueMap(List<String> values) {

        return new HashMap<>() {
            {
                for (int i = 0; i < fieldNames.size(); i++) {
                    String value = values.get(i);
                    if (value.contains("\"")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    String key = fieldNames.get(i);
                    put(key, value);
                }
            }
        };
    }
    @Getter
    static class NullValueFlag{
        private Integer count;

        public NullValueFlag() {
            this.count = 0;
        }

        public void increment() {
            count++;
        }
    }
}
