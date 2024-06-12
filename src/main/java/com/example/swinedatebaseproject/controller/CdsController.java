//package com.example.swinedatebaseproject.controller;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.example.swinedatebaseproject.constant.MyBatisConstants;
//import com.example.swinedatebaseproject.domain.Cds;
//import com.example.swinedatebaseproject.service.CdsService;
//import com.example.swinedatebaseproject.util.ResponseResultUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @Author 刘铭康
// * @Date  2022/11/14
// */
//@RestController
//@RequestMapping(value = "/cds",method = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST})
//public class CdsController {
//    @Autowired
//    CdsService cdsService;
//
//    @GetMapping("/id/{id}")
//    public ResponseResult getCdsById(@PathVariable Integer id) {
//        Cds cds = cdsService.getById(id);
//        return ResponseResultUtils.getResponseResult(Cds.class, cds);
//    }
//
//    @GetMapping("/all")
//    public ResponseResult getAllCds() {
//        List<Cds> list = cdsService.list();
//        return ResponseResultUtils.getResponseResult(Cds.class, list);
//
//        }
//
//    @GetMapping("/page/{current}")
//    public ResponseResult getCdsBasePage(@PathVariable Integer current) {
//        Page<Cds> cdsPage = new Page<>(current, MyBatisConstants.PAGE_SIZE);
//        List<Cds> records = cdsService.page(cdsPage).getRecords();
//        return ResponseResultUtils.getResponseResult(Cds.class, records);
//    }
//
//    @GetMapping("/page-count")
//    public ResponseResult getPageCount() {
//        Long totalRows = cdsService.count();
//        Long pageCount = totalRows % MyBatisConstants.PAGE_SIZE == 0 ? totalRows / MyBatisConstants.PAGE_SIZE : totalRows / MyBatisConstants.PAGE_SIZE + 1;
//        // TODO: 2022/11/16 基于路径？？？ 动态获取keyName
//        return ResponseResultUtils.getSuccessResponseResult("pageCount", pageCount);
//    }
//
//    @DeleteMapping ("/delete")
//    public ResponseResult deleteTargetData(@RequestParam("ids") List<String> ids) {
//        if (cdsService.removeByIds(ids)) {
//            return ResponseResult.success(ResponseResultCode.DELETE_SUCCESS.getCode(), ResponseResultCode.DELETE_SUCCESS.getMessage());
//        } else {
//            return ResponseResult.success(ResponseResultCode.DELETE_FAIL.getCode(), ResponseResultCode.DELETE_FAIL.getMessage());
//        }
//    }
//
//    @GetMapping ("/rows-size")
//    public ResponseResult getRowsSize() {
//        Long totalRows = cdsService.count();
//        return ResponseResultUtils.getSuccessResponseResult("rowsSize", totalRows);
//    }
//
//
//
//
//}
