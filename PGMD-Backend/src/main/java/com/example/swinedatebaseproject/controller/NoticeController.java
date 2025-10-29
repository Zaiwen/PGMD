package com.example.swinedatebaseproject.controller;

import com.example.swinedatebaseproject.domain.Notice;
import com.example.swinedatebaseproject.mapper.NoticeMapper;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.service.NoticeService;
import com.example.swinedatebaseproject.util.ResponseResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author dd
 * @Date 2022/11/14
 */
@RequestMapping(value = "/notice", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@RestController
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @Autowired
    NoticeMapper noticeMapper;


    @PostMapping("/add")
    public void addNotice(@RequestBody Notice notice) {
        if (Objects.isNull(notice.getTitle()) || "".equals(notice.getTitle())){
            notice.setTitle("公告");
        }
        if (Objects.isNull(notice.getContent()) || "".equals(notice.getContent())){
            notice.setContent("无内容");
        }
        if (Objects.isNull(notice.getStartTime())){
            notice.setStartTime(LocalDateTime.now());
        }
        if (Objects.isNull(notice.getEndTime())) {
            notice.setEndTime(LocalDateTime.now().plusDays(7));
        }
        noticeService.save(notice);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteNotice(@PathVariable("id") Integer id) {
        noticeService.removeById(id);
    }

/**
 * 获取所有公告信息，并按照结束时间进行排序。
 *
 * 该接口不需要接收任何参数，返回所有未过期公告的信息列表。
 * 公告的过期判断已经被注释掉，意味着当前所有公告都会被返回。
 * 返回的结果中，公告列表按照结束时间从早到晚进行排序。
 *
 * @return 返回一个包含所有公告信息的ResponseResult对象，其中数据部分为排序后的公告列表。
 */
@GetMapping("/get")
public ResponseResult getNotices() {
    // 从公告服务获取所有公告列表
    List<Notice> list = noticeService.list();
    List<Notice> notices = new ArrayList<>();
    for (Notice notice : list) {
        LocalDateTime endTime = notice.getEndTime();
        // 此处代码被注释，原意是跳过已过期的公告
        // if (endTime.isBefore(LocalDateTime.now())) {
        //     continue;
        // }
        notices.add(notice);
    }

    // 使用Stream API对公告列表按结束时间进行排序
    notices = notices.stream().sorted((o1, o2) -> {
        if (o1.getEndTime().isBefore(o2.getEndTime())) {
            return -1;
        } else if (o1.getEndTime().isAfter(o2.getEndTime())) {
            return 1;
        } else {
            return 0;
        }
    }).toList();

    // 构造并返回成功响应结果，包含排序后的公告列表
    return ResponseResultUtils.getSuccessResponseResult("notices", notices);}

    @GetMapping("/getList")
    public ResponseResult getNoticeLists() {
        // 从公告服务获取所有公告列表
        List<Notice> list = noticeMapper.selectLists();
        // 构造并返回成功响应结果，包含排序后的公告列表
        return ResponseResultUtils.getSuccessResponseResult("notices", list);
    }



}
