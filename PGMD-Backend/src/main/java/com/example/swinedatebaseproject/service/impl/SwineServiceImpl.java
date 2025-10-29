package com.example.swinedatebaseproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swinedatebaseproject.domain.Swine;
import com.example.swinedatebaseproject.mapper.SwineMapper;
import com.example.swinedatebaseproject.service.SwineService;
import org.springframework.stereotype.Service;

@Service
public class SwineServiceImpl extends ServiceImpl<SwineMapper, Swine>
implements SwineService {
}
