package com.example.swinedatebaseproject.service.impl;

import com.example.swinedatebaseproject.domain.vo.LefseVO;
import com.example.swinedatebaseproject.mapper.LefseMapper;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.service.LefseSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LefseServiceImpl implements LefseSevice {
    @Autowired
    private LefseMapper lefseMapper;

    @Override
    public ResponseResult searchLefse(String name) {
        List<LefseVO> list = lefseMapper.findSimilarProjects(name);
        return ResponseResult.success(list);
    }
}
