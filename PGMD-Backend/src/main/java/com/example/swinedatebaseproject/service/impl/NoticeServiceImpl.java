package com.example.swinedatebaseproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swinedatebaseproject.domain.Notice;
import com.example.swinedatebaseproject.mapper.NoticeMapper;
import com.example.swinedatebaseproject.service.NoticeService;
import org.springframework.stereotype.Service;

/**
* @author s1mple
* @description 针对表【notice】的数据库操作Service实现
* @createDate 2023-02-26 16:17:59
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
    implements NoticeService{

}




