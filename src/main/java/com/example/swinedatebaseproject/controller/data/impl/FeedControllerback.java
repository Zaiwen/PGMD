package com.example.swinedatebaseproject.controller.data.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.swinedatebaseproject.constant.MyBatisConstants;
import com.example.swinedatebaseproject.domain.Feed;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.response.ResponseResultCode;
import com.example.swinedatebaseproject.service.FeedService;
import com.example.swinedatebaseproject.util.ResponseResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Feedback")
public class FeedControllerback {
    @Autowired
    FeedService feedService;

    @GetMapping("/id/{id}")
    public ResponseResult getFeedById(@PathVariable Integer id){
        Feed feed = feedService.getById(id);
        return ResponseResultUtils.getResponseResult(Feed.class,feed);
    }

    @GetMapping("/all")
    public ResponseResult getAllFeed(){
        List<Feed> list = feedService.list();
        return ResponseResultUtils.getResponseResult(Feed.class,list);
    }

    @GetMapping("/page/{current}")
    public  ResponseResult getSwineBasePage(@PathVariable Integer current){
        Page<Feed> Feedpage = new Page<>(current, MyBatisConstants.PAGE_SIZE);

        List<Feed> records = feedService.page(Feedpage).getRecords();
        return ResponseResultUtils.getResponseResult(Feed.class,records);
    }

    @GetMapping("/page-count")
    public ResponseResult getPageCount(){
        Long totalRows = feedService.count();
        Long pageCount = totalRows%MyBatisConstants.PAGE_SIZE==0? totalRows/MyBatisConstants.PAGE_SIZE: totalRows / MyBatisConstants.PAGE_SIZE + 1;
        return ResponseResultUtils.getSuccessResponseResult("pageCount",pageCount);
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteTargetData(@RequestParam("ids") List<String> ids){
        if(feedService.removeByIds(ids)){
            return ResponseResult.success(ResponseResultCode.DELETE_SUCCESS.getCode(), ResponseResultCode.DELETE_SUCCESS.getMessage());
        } else {
            return ResponseResult.success(ResponseResultCode.DELETE_FAIL.getCode(), ResponseResultCode.DELETE_FAIL.getMessage());
        }

    }

    @GetMapping ("/rows-size")
    public ResponseResult getRowsSize() {
        Long totalRows = feedService.count();
        return ResponseResultUtils.getSuccessResponseResult("rowsSize", totalRows);
    }
}
