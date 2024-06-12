package com.example.swinedatebaseproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swinedatebaseproject.domain.Metabolism;
import com.example.swinedatebaseproject.mapper.MetabolismMapper;
import com.example.swinedatebaseproject.service.MetabolismService;
import org.springframework.stereotype.Service;

@Service
public class MetabolismImpl extends ServiceImpl<MetabolismMapper, Metabolism>
implements MetabolismService {
}
