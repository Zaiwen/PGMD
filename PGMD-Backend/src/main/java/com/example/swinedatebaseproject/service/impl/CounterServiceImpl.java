package com.example.swinedatebaseproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swinedatebaseproject.domain.Counter;
import com.example.swinedatebaseproject.mapper.CounterMapper;
import com.example.swinedatebaseproject.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl extends ServiceImpl<CounterMapper, Counter>
        implements CounterService {
    @Autowired
    private CounterMapper counterMapper;
    @Override
    public Counter getCounterByPage(String page) {
        return counterMapper.selectByPage(page);
    }
    @Override
    public void updateCounterByPage(String page) {
        Counter counter = counterMapper.selectByPage(page);
        if (counter == null) {
            counter = new Counter();
            counter.setPage(page);
            counter.setCount(1);
            counterMapper.insert(counter);
        } else {
            counter.setCount(counter.getCount() + 1);
            counterMapper.updateById(counter);
//            System.out.println(counter.getCount());
        }
    }
}
