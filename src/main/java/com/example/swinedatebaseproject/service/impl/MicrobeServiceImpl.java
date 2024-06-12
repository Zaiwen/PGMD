package com.example.swinedatebaseproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swinedatebaseproject.domain.Microbe;
import com.example.swinedatebaseproject.mapper.MicrobeMapper;
import com.example.swinedatebaseproject.service.MicrobeService;
import org.springframework.stereotype.Service;

@Service
public class MicrobeServiceImpl extends ServiceImpl<MicrobeMapper, Microbe>
implements MicrobeService {
}
