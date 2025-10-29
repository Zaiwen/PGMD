package com.example.swinedatebaseproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swinedatebaseproject.domain.Feed;
import com.example.swinedatebaseproject.mapper.FeedMapper;
import com.example.swinedatebaseproject.service.FeedService;
import org.springframework.stereotype.Service;

@Service
public class FeedImpl extends ServiceImpl<FeedMapper, Feed>
implements FeedService {
}
