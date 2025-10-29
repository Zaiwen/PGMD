package com.example.swinedatebaseproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.swinedatebaseproject.domain.Project;
import com.example.swinedatebaseproject.domain.dto.SearchDataDTO;
import com.example.swinedatebaseproject.domain.dto.SearchDataFuzzyDTO;
import com.example.swinedatebaseproject.domain.dto.SearchFormDTO;
import com.example.swinedatebaseproject.domain.dto.UploadRequestDTO;
import com.example.swinedatebaseproject.response.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
* @author s1mple
* @description 针对表【samples】的数据库操作Service
* @createDate 2023-02-26 00:20:22
*/
@Service
public interface ProjectService extends IService<Project> {

    ResponseResult searchInfo(String name, Integer currentPage);

    ResponseResult findProjectsByCondition(Project project);

    ResponseResult countProject(String bioProject);

    ResponseResult countCategory();

    ResponseResult countCountry();

    ResponseResult countRuns();

    ResponseResult countIsolation();

    ResponseResult searchProject(SearchFormDTO searchFormDTO);

    ResponseResult CountryNumber();

    ResponseResult searchLefse(String name);

    ResponseResult searchShowData();



    ResponseResult searchProjectByName(SearchDataDTO dto);

    ResponseResult searchProjectOrRuns(SearchDataFuzzyDTO dto);

//    ResponseEntity<ResponseResult<Resource>> downloadFile(String name);

//    int upload(MultipartFile file);

    ResponseResult uploadActual(MultipartFile file);


    ResponseResult uploadFile(UploadRequestDTO dto, MultipartFile metaDataFile, MultipartFile classificationFile, MultipartFile lefseFile, MultipartFile picrust2File);
}
