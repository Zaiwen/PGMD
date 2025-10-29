package com.example.swinedatebaseproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swinedatebaseproject.constant.CommonConstants;
import com.example.swinedatebaseproject.domain.*;
import com.example.swinedatebaseproject.domain.dto.*;
import com.example.swinedatebaseproject.mapper.UploadMapper;
import com.example.swinedatebaseproject.util.FileUtils;
import com.example.swinedatebaseproject.util.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import com.example.swinedatebaseproject.domain.vo.*;
import com.example.swinedatebaseproject.mapper.MicrobialMapper;
import com.example.swinedatebaseproject.mapper.ProjectMapper;
import com.example.swinedatebaseproject.mapper.SamplesMapper;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.response.ResponseResultCode;
import com.example.swinedatebaseproject.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
* @author s1mple
* @description 针对表【Project】的数据库操作Service实现
* @createDate 2023-02-26 00:20:22
*/
@Service
 public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
    implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private SamplesMapper samplesMapper;

    @Autowired
    private MicrobialMapper microbialMapper;

    @Autowired
    private UploadMapper uploadMapper;

    private static final String DOWNLOAD_DIR = "/root/feidian/file"; // 服务器上文件的路径
//    private static final String DOWNLOAD_DIR = "C:\\Users\\space dandy\\Desktop\\file";

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public ResponseResult searchInfo(String name, Integer currentPage) {
        int pageSize =10;
        List<ProjectDTO> projectDetails = projectMapper.findProjectDetails(name, currentPage, pageSize);
        return ResponseResult.success(projectDetails);

    }

    @Override
    public ResponseResult findProjectsByCondition(Project project) {
        List<Project> projectsByCondition = projectMapper.findProjectsByCondition(project);
        return ResponseResult.success(projectsByCondition);
    }

    @Override
    public ResponseResult countProject(String bioProject) {
        int lefseCount = projectMapper.getLefseCountByBioProject(bioProject);
        int nutrientCompositionCount = projectMapper.getNutrientCompositionCountByBioProject(bioProject);
        int sampleRunsCount = projectMapper.getSampleRunsCountByBioProject(bioProject);
        int microbialCount = projectMapper.getMicrobialCountByBioProject(bioProject);
        DataCountVO dataCountVO = new DataCountVO(lefseCount, nutrientCompositionCount, sampleRunsCount, microbialCount,0);
        return ResponseResult.success(dataCountVO);
    }

    @Override
    public ResponseResult countCategory() {
        // 初始查询，获取带有Category字段的结果列表
        List<CountCategoryVO> rawCategoryList = projectMapper.countCategory();
        Map<String, Integer> categoryCountMap = new HashMap<>();

        for (CountCategoryVO vo : rawCategoryList) {
            // 分割Category字段
            String[] categories = vo.getCategory().split(";");

            for (String category : categories) {
                // 过滤掉空字符串（如果有）
                if (!category.trim().isEmpty()) {
                    // 累加每个类别的计数
                    categoryCountMap.put(category.trim(), categoryCountMap.getOrDefault(category.trim(), 0) + vo.getCountProject());
                }
            }
        }

        // 将Map转换为List
        List<CountCategoryVO> categoryList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : categoryCountMap.entrySet()) {
            CountCategoryVO vo = new CountCategoryVO();
            vo.setCategory(entry.getKey());
            vo.setCountProject(entry.getValue());
            categoryList.add(vo);
        }

        // 返回最终的计算结果
        return ResponseResult.success(categoryList);
    }

    @Override
    public ResponseResult countCountry() {
        List<CountCountryVO> list =projectMapper.countCountry();
        return ResponseResult.success(list);
    }

    @Override
    public ResponseResult countRuns() {
        List<CountRunsVO> list =projectMapper.countRuns();
        return ResponseResult.success(list);
    }

    @Override
    public ResponseResult countIsolation() {
        // 初始查询，获取带有Isolation_Location字段的结果列表
        List<CountIsolationVO> rawIsolationList = projectMapper.countIsolation();
        Map<String, Integer> isolationCountMap = new HashMap<>();

        for (CountIsolationVO vo : rawIsolationList) {
            String isolationStr = vo.getIsolation();
            if (isolationStr != null) {
                // 分割Isolation_Location字段
                String[] isolations = isolationStr.split(";");
                for (String isolation : isolations) {
                    // 过滤掉空字符串（如果有）
                    if (!isolation.trim().isEmpty()) {
                        // 累加每个隔离地点的计数
                        isolationCountMap.put(isolation.trim(), isolationCountMap.getOrDefault(isolation.trim(), 0) + vo.getCountProject());
                    }
                }
            }
        }

        // 将Map转换为List
        List<CountIsolationVO> isolationList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : isolationCountMap.entrySet()) {
            CountIsolationVO vo = new CountIsolationVO();
            vo.setIsolation(entry.getKey());
            vo.setCountProject(entry.getValue());
            isolationList.add(vo);
        }

        // 返回最终的计算结果
        return ResponseResult.success(isolationList);
    }

    /**
     * 根据提供的搜索表单数据搜索项目信息。
     *
     * @param searchFormDTO 搜索表单数据传输对象，包含不同类型的动物数据和国家信息。
     * @return 返回搜索结果的响应结果对象，其中包含搜索到的项目信息列表。
     */
    @Override
    public ResponseResult searchProject(SearchFormDTO searchFormDTO) {
        List<ProjectVO> list = new ArrayList<>();
        // 初始化动物数据映射，将不同类型的动物数据与其标识对应
        Map<String, AnimalData> animalDataMap = new HashMap<>();
//        animalDataMap.put("Boar", searchFormDTO.getBoar());
//        animalDataMap.put("Piglets", searchFormDTO.getPiglet());
//        animalDataMap.put("Sow", searchFormDTO.getSow());
//        animalDataMap.put("Growing-finishing Pigs", searchFormDTO.getGrowingFinishingPigs());
        if (searchFormDTO.getPiglet() != null && searchFormDTO.getBoar().isAllFieldsNotNullOrEmpty()){
            animalDataMap.put("Boar", searchFormDTO.getBoar());
        }
        if (searchFormDTO.getPiglet() != null && searchFormDTO.getPiglet().isAllFieldsNotNullOrEmpty()) {
            animalDataMap.put("Piglets", searchFormDTO.getPiglet());
        }

        if (searchFormDTO.getSow() != null && searchFormDTO.getSow().isAllFieldsNotNullOrEmpty()) {
            animalDataMap.put("Sow", searchFormDTO.getSow());
        }

        if (searchFormDTO.getGrowingFinishingPigs() != null && searchFormDTO.getGrowingFinishingPigs().isAllFieldsNotNullOrEmpty()) {
            animalDataMap.put("Growing-finishing Pigs", searchFormDTO.getGrowingFinishingPigs());
        }

        List<String> countries = searchFormDTO.getCountries();

        // 遍历动物数据映射，处理每种类型的动物数据
        // 遍历动物数据映射表，对每个动物数据条目进行处理
        for (Map.Entry<String, AnimalData> entry : animalDataMap.entrySet()) {
            AnimalData animalData = entry.getValue();
            if (animalData != null) {
                // 当没有指定国家列表时，处理所有动物数据而不区分国家
                if(countries.isEmpty()){
                    list.addAll(processAnimalData(entry.getKey(), animalData, ""));
                }else {
                    // 当有指定国家列表时，为每个指定国家分别处理动物数据
                    for (String country : countries) {
                        list.addAll(processAnimalData(entry.getKey(), animalData, country));
                    }
                }
            }else if(!countries.isEmpty()){
                // 如果动物数据为空但指定了国家列表，则根据指定国家查询相关项目数据
                for (String country : countries) {
                    list.addAll(projectMapper.searchProject(null, null, null, null, null,country));
                }
            }
        }


        return ResponseResult.success(list);
    }


    private List<ProjectVO> processAnimalData(String type, AnimalData animalData,String country) {
        String breed = animalData.getBreed();
        String growthStage = animalData.getGrowthStage();
        String phenotypes = animalData.getPhenotypes();
        String isolationLocation = animalData.getIsolationLocation();
        return projectMapper.searchProject(type, breed, growthStage, phenotypes, isolationLocation,country);
    }


    @Override
    public ResponseResult CountryNumber() {
        List<CountryProjectCount> list = projectMapper.getCountryProjectCounts();
        return ResponseResult.success(list);
    }

    @Override
    public ResponseResult searchLefse(String name) {
        List<LefseVO> list = projectMapper.getLefseByBioProject(name);
        return ResponseResult.success(list);
    }

    @Override
    public ResponseResult searchShowData() {
        List<ShowDataVO> list = projectMapper.getShowDataVOs();
        return ResponseResult.success(list);
    }

    @Override
    public ResponseResult searchProjectByName(SearchDataDTO dto) {
        String name = dto.getName();

        // 如果name为空，将其设置为空字符串
        if (name == null) {
            name = "";
        }

        // 第一个条件，如果phenoTypeId是0，返回sample相关的list
        if (dto.getPhenoTypeId() == 0) {
            List<Samples> list = samplesMapper.searchSampleByName(name);
            return ResponseResult.success(list);
        }

        // 第二个条件，如果phenoTypeId是1，返回project相关的list
        else if (dto.getPhenoTypeId() == 1) {
            List<ProjectVO> list = projectMapper.searchProjectByName(name);
            return ResponseResult.success(list);
        }

        // 第三个条件，如果phenoTypeId是2，返回microbial相关的list
        else if (dto.getPhenoTypeId() == 2) {
            List<Microbial> list = microbialMapper.getMicrobial(name);
            return ResponseResult.success(list);
        }

        // 如果phenoTypeId不是0或1，返回一个错误或者空的结果
        else {
            return ResponseResult.error(ResponseResultCode.ID_ERROR.getCode(), ResponseResultCode.ID_ERROR.getMessage());
        }
    }

    @Override
    public ResponseResult searchProjectOrRuns(SearchDataFuzzyDTO dto) {
        if (dto.getPhenoTypeId() == 0) {
            List<Samples> list = samplesMapper.searchSampleInfo(dto);
            return ResponseResult.success(list);
        }

        // 第二个条件，如果phenoTypeId是1，返回project相关的list
        else if (dto.getPhenoTypeId() == 1) {
            List<ProjectVO> list = projectMapper.searchProjectInfo(dto);
            return ResponseResult.success(list);
        }

        // 如果phenoTypeId不是0或1，返回一个错误或者空的结果
        else {
            return ResponseResult.error(ResponseResultCode.ID_ERROR.getCode(), ResponseResultCode.ID_ERROR.getMessage());
        }
    }

//    @Override
//    public ResponseEntity<Resource> downloadFile(String name) {
//
//        String filename=name+".xlsx";
//        try {
//            // 构建文件的绝对路径
//            Path filePath = Paths.get(DOWNLOAD_DIR, filename);
//
//            // 创建Resource对象，将文件包装为Resource
//            Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());
//
//            // 检查文件是否存在
//            if (resource.exists()) {
//                // 设置响应头
//                HttpHeaders headers = new HttpHeaders();
//                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
//
//                // 返回响应实体，以便前端下载文件
//                return ResponseEntity.ok()
//                        .headers(headers)
//                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                        .body(resource);
//            } else {
//                // 如果文件不存在，返回适当的响应并添加自定义消息
//                HttpHeaders headers = new HttpHeaders();
//                headers.add("X-Custom-Message", "File not found");
//
//                return ResponseEntity.noContent()
//                        .headers(headers)
//                        .build();
//            }
//        } catch (IOException e) {
//            // 处理异常情况并添加自定义消息
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("X-Custom-Message", "Server error");
//
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .headers(headers)
//                    .build();
//        }
//
//    }

//    @Override
//    public ResponseResult<Resource> downloadFile(String name) {
//        String filename = name + ".xlsx";
//        try {
//            // 构建文件的绝对路径
//            Path filePath = Paths.get(DOWNLOAD_DIR, filename);
//
//            // 创建Resource对象，将文件包装为Resource
//            Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());
//
//            // 检查文件是否存在
//            if (resource.exists()) {
//                // 设置响应头
//                HttpHeaders headers = new HttpHeaders();
//                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
//
//                // 返回响应实体，以便前端下载文件
//                return ResponseResult.success("200", "File found and ready for download", resource);
//            } else {
//                // 如果文件不存在，设置相关信息到ResponseResult
//                return ResponseResult.success("204", "目前没有相关文件");
//            }
//        } catch (IOException e) {
//            // 处理异常情况
//            e.printStackTrace();
//            return ResponseResult.error("500", "Server error");
//        }
//    }


    public void fileDownload(String name, HttpServletResponse response, HttpServletRequest request) throws IOException {
        try {
            String filename = name + ".xlsx";
            // 构建文件的绝对路径
            Path filePath = Paths.get(DOWNLOAD_DIR, filename);
            String path = DOWNLOAD_DIR + filename;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, filename);  // 直接使用filename
            FileUtils.writeBytes(path, response.getOutputStream());

        } catch (IOException e) {
            // 处理异常情况并返回失败的JSON响应
            ResponseResult<Object> errorResponse = ResponseResult.error("500", "文件下载失败");
            String value = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(value);
        }
    }





    // 上传文件，但仅根据文件类型来判断
    public ResponseResult uploadActual(MultipartFile multipartFile) {
        // 获取操作系统名称
        String systemProperty = System.getProperty("os.name");

        // 生成唯一的文件名后缀
        long fileSuffix = System.currentTimeMillis();

        // 获取原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.indexOf(".");
        // 获取文件类型后缀
        String fileType = originalFilename.substring(i);
        // 构建新的文件名，包含时间戳
        String fileName = originalFilename.substring(0, i) + "_" + fileSuffix + fileType;

        // 检查文件类型是否在允许的文件类型列表中
        if (Boolean.FALSE.equals(CommonConstants.FILE_TYPES.contains(fileType))) {
            return ResponseResult.error(ResponseResultCode.FILE_TYPE_ERROR.getCode(), ResponseResultCode.FILE_TYPE_ERROR.getMessage());
        }

        // 根据操作系统类型选择不同的文件保存路径
        if (systemProperty.toUpperCase().contains("WINDOW")) {
            return saveData(multipartFile, CommonConstants.WINDOW_FILE_PATH_TEST,fileName);
        } else if (systemProperty.toUpperCase().contains("LINUX")) {
            return saveData(multipartFile, CommonConstants.LINUX_FILE_PATH,fileName);
        } else {
            // 未识别的操作系统类型
            return ResponseResult.error(ResponseResultCode.ADD_FAIL.getCode(), ResponseResultCode.ADD_FAIL.getMessage());
        }
    }

    @Override
    public ResponseResult uploadFile(UploadRequestDTO dto, MultipartFile metaDataFile, MultipartFile classificationFile, MultipartFile lefseFile, MultipartFile picrust2File) {
        Upload upload = new Upload();
        try {
            String name = SecurityUtils.getUsername();
            upload.setCreateBy(name);
        } catch (Exception e) {
            // 如果用户 ID 不可用，处理可能出现的异常。
            e.printStackTrace();
        }
        // 将非空的字段从 dto 复制到 upload
        if (!StringUtils.isEmpty(dto.getEmail())) {
            upload.setEmail(dto.getEmail());
        }
        if (!StringUtils.isEmpty(dto.getType())) {
            upload.setType(dto.getType());
        }
        if (!StringUtils.isEmpty(dto.getPaperTitle())) {
            upload.setPaperTitle(dto.getPaperTitle());
        }
        if (!StringUtils.isEmpty(dto.getProjectId())) {
            upload.setProjectId(dto.getProjectId());
        }
        if (!StringUtils.isEmpty(dto.getIntroduction())) {
            upload.setIntroduction(dto.getIntroduction());
        }
        if (!StringUtils.isEmpty(dto.getFeedback())) {
            upload.setFeedback(dto.getFeedback());
        }
        if (!StringUtils.isEmpty(dto.getAnalysisResult())) {
            upload.setAnalysisResult(dto.getAnalysisResult());
        }

        // 检查四个文件是否都为 null
        if (metaDataFile == null && classificationFile == null && lefseFile == null && picrust2File == null) {
            return ResponseResult.error(ResponseResultCode.FILE_NULL.getCode(), ResponseResultCode.FILE_NULL.getMessage());
        }

        // 如果四个文件都不为 null，继续执行其他逻辑

        // 保存 metaDataFile
        if (metaDataFile!=null) {
            String filename = metaDataFile.getOriginalFilename();
            saveFile(metaDataFile, CommonConstants.metaDataFilePath,filename);
            String filePath= CommonConstants.metaDataFilePath+filename;
            upload.setMetaDataUrl(filePath);
        }

        // 保存 classificationFile
        if (classificationFile != null) {
            String filename = classificationFile.getOriginalFilename();
            saveFile(classificationFile, CommonConstants.classificationFilePath, filename);
            String filePath = CommonConstants.classificationFilePath + filename;
            upload.setClassificationUrl(filePath);
        }

        // 保存 lefseFile
        if (lefseFile!=null) {
            String filename = lefseFile.getOriginalFilename();
            saveFile(lefseFile, CommonConstants.lefseFilePath, filename);
            String filePath = CommonConstants.lefseFilePath + filename;
            upload.setLefseUrl(filePath);
        }

        // 保存 picrust2File
        if (picrust2File!=null) {
            String filename = picrust2File.getOriginalFilename();
            saveFile(picrust2File, CommonConstants.picrust2FilePath, filename);
            String filePath = CommonConstants.picrust2FilePath + filename;
            upload.setPicrust2Url(filePath);
        }

        // 保存upload
        uploadMapper.insertUpload(upload);

        return ResponseResult.success(ResponseResultCode.SUCCESS);
    }

    private ResponseResult saveData(MultipartFile file, String filePath, String fileName) {
        try {
            // 构建文件路径
            Path path = Path.of(filePath, fileName);

            // 如果目录不存在，创建目录
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // 保存文件
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return ResponseResult.success(ResponseResultCode.SUCCESS); // 或者返回其他成功信息
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.error(ResponseResultCode.ADD_FAIL.getCode(), ResponseResultCode.ADD_FAIL.getMessage());
        }
    }

    private void saveFile(MultipartFile file, String filePath, String fileName) {
        try {
            // 构建文件路径
            Path path = Path.of(filePath, fileName);

            // 如果目录不存在，创建目录
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // 保存文件
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}




