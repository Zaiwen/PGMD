package com.example.swinedatebaseproject.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;

//import javax.servlet.ReadListener;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CommonReadListener<T> implements ReadListener<T> {

    private static final int BATCH_COUNT = 1000;
    private static final int SIZE = 100 << 4;
    private int dataRowsSize;
    private final List<T> cachedDataList = new ArrayList<>(SIZE);
    private final IService<T> service;

    public CommonReadListener(IService<T> service) {
        this.service = service;
        dataRowsSize = 0;
//        System.out.println("13344");
    }

    @Override
    public void invoke(T data, AnalysisContext context) {

        cachedDataList.add(data);
        dataRowsSize++;
//        System.out.println("13344");
//        saveData();

        if (cachedDataList.size() >= BATCH_COUNT) {
//            System.out.println("13344");
            saveData();
            cachedDataList.clear();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        service.saveOrUpdateBatch(cachedDataList);
        log.info("已完成{}条数据添加", dataRowsSize);
    }

    private void saveData(){
        System.out.println("1");
        if(service.saveOrUpdateBatch(cachedDataList))
            log.info("Success!");
        else
            log.info("Fail!");
    }
}
