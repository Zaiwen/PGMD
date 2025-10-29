package com.example.swinedatebaseproject.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.swinedatebaseproject.constant.MyBatisConstants;
import com.example.swinedatebaseproject.domain.CommonUser;
import com.example.swinedatebaseproject.domain.CommonUserDto;
import com.example.swinedatebaseproject.service.CommonUserService;
import com.example.swinedatebaseproject.converter.CommonUserConverter;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.response.ResponseResultCode;
import com.example.swinedatebaseproject.util.ResponseResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


/**
 * @Author 123
 * @Date 2022/11/14
 */
@RestController
@RequestMapping("/user")
public class CommonUserController {

    @Autowired
    CommonUserService commonUserService;

    @GetMapping("/all")
    public ResponseResult getAllUsers() {
        List<CommonUser> commonUsers = commonUserService.list();
        List<CommonUserDto> commonUserDtos = commonUsers.stream().map(CommonUserConverter::ConvertCommonUserDtoFromCommonUser).toList();
        return Objects.requireNonNull(ResponseResult.success(ResponseResultCode.SUCCESS.getCode(), ResponseResultCode.SUCCESS.getMessage(), commonUserDtos));
    }

    @GetMapping("/page/{current}")
    public ResponseResult getCdsBasePage(@PathVariable Integer current) {
        Page<CommonUser> commonUserPage = new Page<>(current, MyBatisConstants.PAGE_SIZE);
        List<CommonUser> commonUsers = commonUserService.page(commonUserPage).getRecords();
        List<CommonUserDto> commonUserDtos = commonUsers.stream().map(CommonUserConverter::ConvertCommonUserDtoFromCommonUser).toList();
        return ResponseResultUtils.getResponseResult(CommonUserDto.class, commonUserDtos);
    }

    @GetMapping("/page-count")
    public ResponseResult getPageCount() {
        Long totalRows = commonUserService.count();
        Long pageCount = totalRows % MyBatisConstants.PAGE_SIZE == 0 ? totalRows / MyBatisConstants.PAGE_SIZE : totalRows / MyBatisConstants.PAGE_SIZE + 1;
        return ResponseResultUtils.getSuccessResponseResult("pageCount", pageCount);
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteTargetData(@RequestParam("ids") List<String> ids) {
        System.out.println(ids);
        if (commonUserService.removeByIds(ids)) {
            return ResponseResult.success(ResponseResultCode.DELETE_SUCCESS.getCode(), ResponseResultCode.DELETE_SUCCESS.getMessage());
        } else {
            return ResponseResult.success(ResponseResultCode.DELETE_FAIL.getCode(), ResponseResultCode.DELETE_FAIL.getMessage());
        }
    }

    @GetMapping ("/rows-size")
    public ResponseResult getRowsSize() {
        Long totalRows = commonUserService.count();
        return ResponseResultUtils.getSuccessResponseResult("rowsSize", totalRows);
    }

}
