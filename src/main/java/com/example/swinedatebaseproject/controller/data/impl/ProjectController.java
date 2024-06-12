package com.example.swinedatebaseproject.controller.data.impl;

import com.example.swinedatebaseproject.controller.data.CommonController;
import com.example.swinedatebaseproject.domain.Project;
import com.example.swinedatebaseproject.domain.dto.SearchDataDTO;
import com.example.swinedatebaseproject.domain.dto.SearchDataFuzzyDTO;
import com.example.swinedatebaseproject.domain.dto.SearchFormDTO;
import com.example.swinedatebaseproject.domain.dto.UploadRequestDTO;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.service.ProjectService;
import com.example.swinedatebaseproject.util.FileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @Author dd
 * @Date 2022/11/14
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/Project",method = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST})
public class ProjectController extends CommonController<Project> {

//    @Resource
    @Autowired
    private ProjectService projectService;
//
    @Autowired
    public ProjectController(ProjectService service) {
        super("Project");
        this.service = service;
    }

//    private final ProjectService projectService;
//
//    @Autowired
//    public ProjectController(@Qualifier("projectService") ProjectService projectService) {
//        super("Project");
//        this.projectService = projectService;
//    }

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
    public ResponseResult updateSingleData(@RequestParam("values")List<String> values, @RequestBody Project object) {
        return updateSingleDataActual(values, object);
    }

    @PostMapping("/save-single-data")
    @Override
    public ResponseResult saveSingleData(@RequestBody Project object) {
        return saveSingleDataActual(object);
    }

    @DeleteMapping("/delete-single-data")
    @Override
    public ResponseResult deleteSingleDataByColumns(@RequestParam List<String> singleRowValues) {
        return deleteSingleDataByColumnsActual(singleRowValues);
    }

    @DeleteMapping("/delete-multi-data-main")
    @Override
    public ResponseResult deleteBatchOnMainTable(@RequestBody List<Project> values) {
        return deleteBatchOnMainTableActual(values);
    }


    @DeleteMapping("/delete-multi-data-sub")
    @Override
    public ResponseResult deleteBatchOnSubTable(@RequestParam List<String> formValues) {
        return deleteBatchOnSubTableActual(formValues);
    }


    // 根据项目名称查找其他四个表的数据
    @GetMapping("/search_info/{name}")
    public ResponseResult searchInfo(@PathVariable String name,@RequestParam(defaultValue = "1")Integer currentPage) {
        return projectService.searchInfo(name,currentPage);
    }

    // 根据信息查找项目或者Samples
    @PostMapping("/search_project_fuzzy")
    public ResponseResult searchProject(@RequestBody Project project) {
        return projectService.findProjectsByCondition(project);
    }


    // 根据项目名称，查找在其他表摘得相关关于该字段的数据量
    @GetMapping("/count_project")
    public ResponseResult searchProject(@RequestParam String bioProject) {
        return projectService.countProject(bioProject);
    }

    @GetMapping("/count_category")
    public ResponseResult countCategory() {
        return projectService.countCategory();
    }

    @GetMapping("/count_country")
    public ResponseResult countCountry() {
        return projectService.countCountry();
    }

    @GetMapping("/count_runs")
    public ResponseResult countRuns() {
        return projectService.countRuns();
    }

    @GetMapping("/count_isolation")
    public ResponseResult countIsolation() {
        return projectService.countIsolation();
    }

    @PostMapping("/search_project")
    public ResponseResult searchProject(@RequestBody SearchFormDTO searchFormDTO) {
        return projectService.searchProject(searchFormDTO);
    }

//    @PostMapping("/upload")
//    @Override
//    public ResponseResult upload(@RequestParam("file") MultipartFile multipartFile) {
//        return projectService.upload(multipartFile);
//    }


    // 查找每个国家的项目数量
    @GetMapping("/getCountryNumber")
    public ResponseResult searchCountryNumber() {
        return projectService.CountryNumber();
    }

    @PostMapping("/search_lefse/{name}")
    public ResponseResult searchLefse(@PathVariable String name) {
        return projectService.searchLefse(name);
    }

    // 首页展示的相关查询
    @GetMapping("/show")
    public ResponseResult searchShowData() {
        return projectService.searchShowData();
    }

    // 根据项目名称查询项目
    @PostMapping("/searchProjectOrSample")
    public ResponseResult searchProjectByName(@RequestBody SearchDataDTO dto) {
        return projectService.searchProjectByName(dto);
    }


    @PostMapping("/searchProjectOrRuns")
    public ResponseResult searchProjectByName(@RequestBody SearchDataFuzzyDTO dto) {
        return projectService.searchProjectOrRuns(dto);
    }



//    @PostMapping("/download/{name}")
//    public void downloadFile(@PathVariable String name,HttpServletResponse response, HttpServletRequest request) {
//        return projectService.downloadFile(name,response,request);
//    }

//    private static final String DOWNLOAD_DIR = "C:\\Users\\space dandy\\Desktop\\file";
      private static final String DOWNLOAD_DIR = "/root/feidian/file"; // 服务器上文件的路径
      private static final String DOWNLOAD_TEMPLATE_FILE = "/root/feidian/example"; // 服务器上文件的路径


    private static final ObjectMapper objectMapper = new ObjectMapper();


//    @PostMapping("/download/{name}")
//    public void downloadFile(@PathVariable String name, HttpServletResponse response, HttpServletRequest request) throws IOException {
//        try {
//            String filename = name + ".xlsx";
//            // 构建文件的绝对路径
//            Path filePath = Paths.get(DOWNLOAD_DIR, filename);
//            String path = DOWNLOAD_DIR + filename;
//
//            if (Files.exists(filePath)) {
//                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//                FileUtils.setAttachmentResponseHeader(response, filename);
//                FileUtils.writeBytes(path, response.getOutputStream());
//            } else {
//                // 如果文件不存在，返回404状态码和相应的JSON响应
//                ResponseResult<Object> errorResponse = ResponseResult.error("404", "File not exist");
//                String value = objectMapper.writeValueAsString(errorResponse);
//                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                response.getWriter().write(value);
//            }
//        } catch (IOException e) {
//            // 处理异常情况并返回失败的JSON响应
//            ResponseResult<Object> errorResponse = ResponseResult.error("500", "文件下载失败");
//            String value = objectMapper.writeValueAsString(errorResponse);
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write(value);
//        }
//    }

    @PostMapping("/download/{name}")
    public ResponseEntity<?> downloadFile(@PathVariable String name, HttpServletResponse response, HttpServletRequest request) throws IOException {
        try {
            String filename = name + ".xlsx";
            // 构建文件的绝对路径
            Path filePath = Paths.get(DOWNLOAD_DIR, filename);
            String path = DOWNLOAD_DIR + filename;

            if (Files.exists(filePath)) {
                // 文件存在，创建Resource对象，将文件包装为Resource
                Resource resource = new UrlResource(filePath.toUri());

                // 设置响应头
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

                // 返回响应实体，以便前端下载文件
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                // 如果文件不存在，返回404状态码和相应的JSON响应
                ResponseResult<Object> errorResponse = ResponseResult.error("404", "文件不存在");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(errorResponse);
            }
        } catch (IOException e) {
            // 处理异常情况并返回失败的JSON响应
            ResponseResult<Object> errorResponse = ResponseResult.error("500", "文件下载失败");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    @PostMapping("/downloadTemplateFile/{name}")
    public ResponseEntity<?> downloadTemplateFile(@PathVariable String name,HttpServletResponse response, HttpServletRequest request) throws IOException {
        try {
            Path filePath;
            // 根据文件名构建不同的文件路径
            switch (name) {
                case "metaDataFile":
                    filePath = Paths.get(DOWNLOAD_TEMPLATE_FILE, "example_sample-metadata.xlsx");
                    break;
                case "classificationFile":
                    filePath = Paths.get(DOWNLOAD_TEMPLATE_FILE, "example_taxonomy_calssification_level-7.csv");
                    break;
                case "lefseFile":
                    filePath = Paths.get(DOWNLOAD_TEMPLATE_FILE, "example_LEfSe.zip");
                    break;
                case "picrust2File":
                    filePath = Paths.get(DOWNLOAD_TEMPLATE_FILE, "example_PICRUSt2.zip");
                    break;
                default:
                    throw new IllegalArgumentException("未知的文件类型: " + name);
            }


            if (Files.exists(filePath)) {
                // 文件存在，创建Resource对象，将文件包装为Resource
                Resource resource = new UrlResource(filePath.toUri());

                // 设置响应头
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

                // 返回响应实体，以便前端下载文件
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                // 如果文件不存在，返回404状态码和相应的JSON响应
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("文件不存在");
            }
        } catch (IOException e) {
            // 处理异常情况并返回失败的JSON响应
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("文件下载失败");
        }

    }


    @PostMapping("/uploadFile")
    public ResponseResult uploadFile( @RequestParam(required = false) String email,
                                      @RequestParam(required = false) String type,
                                      @RequestParam(required = false) String paperTitle,
                                      @RequestParam(required = false) String projectId,
                                      @RequestParam(required = false) String introduction,
                                      @RequestParam(required = false) String feedback,
                                      @RequestParam(required = false) String analysisResult,
                                     @RequestParam(value = "metaDataFile",required = false) MultipartFile metaDataFile,
                                     @RequestParam(value = "classificationFile",required = false) MultipartFile classificationFile,
                                     @RequestParam(value = "lefseFile",required = false) MultipartFile lefseFile,
                                     @RequestParam(value = "picrust2File",required = false) MultipartFile picrust2File) {
        // 将额外参数的值设置到 UploadRequestDTO 对象中
        UploadRequestDTO dto = new UploadRequestDTO(email, type, paperTitle, projectId, introduction, feedback, analysisResult);

        // 调用服务方法上传文件，并传递 UploadRequestDTO 对象
        return projectService.uploadFile(dto, metaDataFile, classificationFile, lefseFile, picrust2File);
    }




}
