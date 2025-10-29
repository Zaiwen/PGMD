package com.example.swinedatebaseproject.controller.data.impl;

import com.example.swinedatebaseproject.controller.data.CommonController;
import com.example.swinedatebaseproject.domain.Microbial;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.service.MicrobialService;
import com.example.swinedatebaseproject.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author dd
 * @Date 2022/11/14
 */

@RestController
@RequestMapping(value = "/microbial",method = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST})
public class MicrobialController extends CommonController<Microbial> {

    @Resource
    private MicrobialService microbialService;

    @Autowired
    public MicrobialController(MicrobialService service) {
        super("microbial_name");
        this.service = service;
    }

    @GetMapping("/fieldNames")
    @Override
    public ResponseResult getFieldNames() {
        return getFieldNamesActual();
    }

    @GetMapping("/id/{id}")
    @Override
    public ResponseResult getSingleDataById(@PathVariable Integer id) {
        return getSingleDataByIdActual(id);
    }

    @GetMapping("/all")
    @Override
    public ResponseResult getAllData() {
        return getAllDataActual();
    }

    @GetMapping("/page/{current}")
    @Override
    public ResponseResult getDataByPage(@PathVariable Integer current){
        return getDataByPageActual(current);
    }

    @GetMapping("/page-count")
    @Override
    public ResponseResult getPageCount() {
        return getPageCountActual();
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseResult deleteByIds(@RequestParam("ids") List<String> ids) {
        return deleteByIdsActual(ids);
    }

    @GetMapping ("/rows-size")
    @Override
    public ResponseResult getRowsSize() {
        return getRowsSizeActual();
    }

    @GetMapping("/search/{name}")
    @Override
    public ResponseResult getData(@PathVariable String name,@RequestParam(defaultValue = "1")Integer currentPage) {
        return getDataActual(name,currentPage);
    }

    @GetMapping("/search_precise/{name}")
    @Override
    public ResponseResult getData_precise(@PathVariable String name,@RequestParam(defaultValue = "1")Integer currentPage) {
        return getDataActual_precise(name,currentPage);
    }

    @PostMapping("/upload")
    @Override
    public ResponseResult upload(@RequestParam("file") MultipartFile multipartFile) {
        return uploadActual(multipartFile);
    }

    @GetMapping("/search-multi-key")
    @Override
    public ResponseResult searchValueByMultiKey(@RequestParam("formValues") List<String> values, @RequestParam("currentPage") Integer currentPage) {
        return searchValueByMultiKeyActual(values, currentPage);
    }

    @PutMapping("/update-single-data")
    @Override
    public ResponseResult updateSingleData(@RequestParam("values")List<String> values, @RequestBody Microbial object) {
        return updateSingleDataActual(values, object);
    }

    @PostMapping("/save-single-data")
    @Override
    public ResponseResult saveSingleData(@RequestBody Microbial object) {
        return saveSingleDataActual(object);
    }

    @DeleteMapping("/delete-single-data")
    @Override
    public ResponseResult deleteSingleDataByColumns(@RequestParam List<String> singleRowValues) {
        return deleteSingleDataByColumnsActual(singleRowValues);
    }

    @DeleteMapping("/delete-multi-data-main")
    @Override
    public ResponseResult deleteBatchOnMainTable(@RequestBody List<Microbial> values) {
        return deleteBatchOnMainTableActual(values);
    }


    @DeleteMapping("/delete-multi-data-sub")
    @Override
    public ResponseResult deleteBatchOnSubTable(@RequestParam List<String> formValues) {
        return deleteBatchOnSubTableActual(formValues);
    }

    // 查找level值出现的次数
    @GetMapping("/count_level")
    public ResponseResult countLevelByName() {
        return microbialService.countLevelByName();
    }

    @GetMapping("/search_microbial/{name}")
    public ResponseResult searchMicrobial(@PathVariable String name) {
        return microbialService.searchMicrobial(name);
    }

    @GetMapping("/search_microbial_byProject/{name}")
    public ResponseResult searchMicrobialByProject(@PathVariable String name) {
        return microbialService.searchMicrobialByProject(name);
    }

    // 根据项目名称查询项目内的微生物分组和占比
    @GetMapping("/count_group/{name}")
    public ResponseResult countGroup(@PathVariable String name) {
        return microbialService.countGroup(name);
    }




}
