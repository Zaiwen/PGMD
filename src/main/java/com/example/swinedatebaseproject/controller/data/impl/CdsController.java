//package com.example.swinedatebaseproject.controller.data.impl;
//
//import com.example.swinedatebaseproject.controller.data.CommonController;
//import com.example.swinedatebaseproject.response.ResponseResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
///**
// * @Author 刘铭康
// * @Date 2022/11/14
// */
//
//@RestController
//@RequestMapping(value = "/cds",method = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST})
//public class CdsController extends CommonController<Cds> {
//
//    @Autowired
//    public CdsController(CdsService service) {
//        super("cds_name");
//        this.service = service;
//    }
//
//    @GetMapping("/id/{id}")
//    @Override
//    public ResponseResult getDataById(@PathVariable Integer id) {
//        return getDataByIdActual(id);
//    }
//
//    @GetMapping("/all")
//    @Override
//    public ResponseResult getAllData() {
//        return getAllDataActual();
//    }
//
//    @GetMapping("/page/{current}")
//    @Override
//    public ResponseResult getDataBasePage(@PathVariable Integer current) {
//        return getDataBasePageActual(current);
//    }
//
//    @GetMapping("/page-count")
//    @Override
//    public ResponseResult getPageCount() {
//        return getPageCountActual();
//    }
//
//    @DeleteMapping("/delete")
//    @Override
//    public ResponseResult deleteTargetData(@RequestParam("ids") List<String> ids) {
//        return deleteTargetDataActual(ids);
//    }
//
//    @GetMapping ("/rows-size")
//    @Override
//    public ResponseResult getRowsSize() {
//        return getRowsSizeActual();
//    }
//    @GetMapping("/search/{name}")
//
//    @Override
//    public ResponseResult getData(@PathVariable String name) {
//        return getDataActual(name);
//    }
//
//    @PostMapping("/upload")
//    @Override
//    public ResponseResult upload(@RequestParam("file") MultipartFile multipartFile) {
//        return uploadActual(multipartFile);
//    }
//}
