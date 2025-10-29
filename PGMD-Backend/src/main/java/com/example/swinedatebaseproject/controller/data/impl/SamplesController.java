package com.example.swinedatebaseproject.controller.data.impl;

import com.example.swinedatebaseproject.controller.data.CommonController;
import com.example.swinedatebaseproject.domain.Samples;
import com.example.swinedatebaseproject.domain.dto.SearchFormDTO;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.service.SamplesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author dd
 * @Date 2022/11/14
 */

@RestController
@RequestMapping(value = "/samples",method = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST})
public class SamplesController extends CommonController<Samples> {

    @Autowired
    private SamplesService service;

    @Autowired
    public SamplesController(SamplesService service) {
        super("Runs");
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
    public ResponseResult getDataByPage(@PathVariable Integer current) {
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
    public ResponseResult updateSingleData(@RequestParam("values")List<String> values, @RequestBody Samples object) {
        return updateSingleDataActual(values, object);
    }

    @PostMapping("/save-single-data")
    @Override
    public ResponseResult saveSingleData(@RequestBody Samples object) {
        return saveSingleDataActual(object);
    }

    @DeleteMapping("/delete-single-data")
    @Override
    public ResponseResult deleteSingleDataByColumns(@RequestParam List<String> singleRowValues) {
        return deleteSingleDataByColumnsActual(singleRowValues);
    }

    @DeleteMapping("/delete-multi-data-main")
    @Override
    public ResponseResult deleteBatchOnMainTable(@RequestBody List<Samples> values) {
        return deleteBatchOnMainTableActual(values);
    }


    @DeleteMapping("/delete-multi-data-sub")
    @Override
    public ResponseResult deleteBatchOnSubTable(@RequestParam List<String> formValues) {
        return deleteBatchOnSubTableActual(formValues);
    }


    /**
     * 根据样品找相关的Lefse
     * @param name
     * @param currentPage
     * @param pageSize
     * @return
     */
//    @GetMapping("/search_lefse/{name}")
//    public ResponseResult searchLefse(@PathVariable String name,@RequestParam(defaultValue = "1")Integer currentPage,@RequestParam Integer pageSize) {
//        return service.searchLefse(name,currentPage,pageSize);
//    }

    // 根据具体样品名称查找该样品所拥有的微生物峰度数据
    @GetMapping("/search_microbial/{name}")
    public ResponseResult searchMicrobial(@PathVariable String name,@RequestParam(defaultValue = "1")Integer currentPage,@RequestParam Integer pageSize) {
        return service.searchMicrobial(name,currentPage,pageSize);
    }

    // 根据样品名称，查找在其他表摘得相关关于该字段的数据量
//    @GetMapping("/count_sample")
//    public ResponseResult countSample(@RequestParam String bioSample) {
//        return service.countSample(bioSample);
//    }

    @PostMapping("/search_sample")
    public ResponseResult searchSample(@RequestBody SearchFormDTO searchFormDTO) {
        return service.searchSample(searchFormDTO);
    }




}
