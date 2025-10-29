package com.example.swinedatebaseproject.controller.user;

import com.example.swinedatebaseproject.domain.CommonUser;
import com.example.swinedatebaseproject.domain.dto.RegisterDTO;
import com.example.swinedatebaseproject.service.RegisterService;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.response.ResponseResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Author 123
 * @Date 2022/11/14
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @PostMapping("/user")
    public ResponseResult userLogin(@RequestBody RegisterDTO commonUser) {
        return  registerService.userRegister(commonUser);

    }

    @PostMapping("/sendEmail")
    public ResponseResult sendEmail(@RequestParam("email") String email, HttpSession session){
        return registerService.sendEmail(email, session);
    }
//    @PostMapping("/administrator")
//    public ResponseResult administratorLogin(@RequestBody Administrator administrator) {
//        if (registerService.administratorRegister(administrator)) {
//            return ResponseResult.success(ResponseResultCode.REGISTER_SUCCESS.getCode(), ResponseResultCode.REGISTER_SUCCESS.getMessage());
//        }else {
//            return ResponseResult.error(ResponseResultCode.REGISTER_FAIL.getCode(), ResponseResultCode.REGISTER_FAIL.getMessage());
//        }
//    }

}
