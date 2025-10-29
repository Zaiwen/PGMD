package com.example.swinedatebaseproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.swinedatebaseproject.domain.Counter;

public interface CounterService extends IService<Counter> {
    Counter getCounterByPage(String page);
    void updateCounterByPage(String page);
}
