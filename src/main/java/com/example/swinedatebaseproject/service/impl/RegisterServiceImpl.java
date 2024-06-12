package com.example.swinedatebaseproject.service.impl;

import com.example.swinedatebaseproject.cache.UserCache;
import com.example.swinedatebaseproject.domain.Administrator;
import com.example.swinedatebaseproject.domain.CodeData;
import com.example.swinedatebaseproject.domain.CommonUser;
import com.example.swinedatebaseproject.domain.dto.RegisterDTO;
import com.example.swinedatebaseproject.mapper.CommonUserMapper;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.response.ResponseResultCode;
import com.example.swinedatebaseproject.service.AdministratorService;
import com.example.swinedatebaseproject.service.CommonUserService;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author 刘铭康
 * @Date 2022/11/14
 */

@Service
public class RegisterServiceImpl implements com.example.swinedatebaseproject.service.RegisterService {

    @Autowired
    CommonUserService commonUserService;

    @Autowired
    AdministratorService administratorService;

    @Autowired
    CommonUserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private final Map<String, CodeData> codeMap = new ConcurrentHashMap<>();
    @Override
    public ResponseResult userRegister(RegisterDTO commonUser) {

        // 1.校验邮箱
        String email = commonUser.getMail();
        // 验证邮箱格式是否正确
        if (!isEmailInvalid(email)) {
            return ResponseResult.error(ResponseResultCode.EMAIL_INVALID.getCode(), ResponseResultCode.EMAIL_INVALID.getMessage());
        }

        // 获取验证码
        CodeData codeData = codeMap.get("login:code:" + email);
        if (codeData == null) {
            return ResponseResult.error(ResponseResultCode.CODE_NOT_FOUND.getCode(), ResponseResultCode.CODE_NOT_FOUND.getMessage());
        }
        String cacheCode = codeData.getCode();
        String code = commonUser.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            // 不一致，报错
            return ResponseResult.error(ResponseResultCode.CODE_MISMATCH.getCode(), ResponseResultCode.CODE_MISMATCH.getMessage());
        }

        // 对数据进行是否已经存在的判断
        if (userMapper.isEmailExist(commonUser.getMail())) {
            return ResponseResult.error(ResponseResultCode.EMAIL_EXIST.getCode(), ResponseResultCode.EMAIL_EXIST.getMessage());
        }
        if (userMapper.isNameExits(commonUser.getName())) {
            return ResponseResult.error(ResponseResultCode.NAME_EXIST.getCode(), ResponseResultCode.NAME_EXIST.getMessage());
        }

        // 对密码是否符合规范的判断
        if (!isPasswordStandardized(commonUser.getPassword())) {
            return ResponseResult.error(ResponseResultCode.PASSWORD_NOT_STANDARDIZED.getCode(), ResponseResultCode.PASSWORD_NOT_STANDARDIZED.getMessage());
        }


        String encodePassword = passwordEncoder.encode(commonUser.getPassword());
        commonUser.setPassword(encodePassword);
        //存入数据库
        userMapper.insertUser(commonUser);

        return ResponseResult.success(ResponseResultCode.REGISTER_SUCCESS.getCode(), ResponseResultCode.REGISTER_SUCCESS.getMessage());
    }


    @Override
    public ResponseResult sendEmail(String email, HttpSession session) {
        // 确保邮箱不为空
        if (!StringUtils.hasText(email)) {
            return ResponseResult.error(ResponseResultCode.REGISTER_FAIL.getCode(), ResponseResultCode.REGISTER_FAIL.getMessage());
        }
        // 验证邮箱是否存在
        if(userMapper.isEmailExist(email)){
            return ResponseResult.error(ResponseResultCode.REGISTER_FAIL.getCode(), ResponseResultCode.REGISTER_FAIL.getMessage());
        }
        // 验证邮箱格式是否正确
        if (!isEmailInvalid(email)) {
            return ResponseResult.error(ResponseResultCode.REGISTER_FAIL.getCode(), ResponseResultCode.REGISTER_FAIL.getMessage());
        }
        // 生成验证码
        String code = generateRandomCode(6);//帮我写个随机生成6位随机数字的验证码方法
        // 发送验证吗
        sendEmail(email,code);


        // 设置验证码的失效时间为3分钟
        long expirationTimeMillis = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3);

        // 保存验证码和失效时间到ConcurrentHashMap
        codeMap.put("login:code:" + email, new CodeData(code, expirationTimeMillis));


        return ResponseResult.success();
    }



    @Override
    public boolean administratorRegister(Administrator administrator) {
        if (UserCache.ADMINISTRATORS.contains(administrator.getName())) {
            return false;
        } else {
            administratorService.save(administrator);
            UserCache.COMMON_USERS.add(administrator.getName());
            return true;
        }
    }

    public boolean isEmailInvalid(String email){
        return email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    }

    public boolean isPasswordStandardized(String password) {
        // 检查密码长度是否在8到16位之间
        if (password.length() < 8 || password.length() > 16) {
            return false;
        }

        int digitCount = 0;     // 记录数字出现次数
        int letterCount = 0;    // 记录字母出现次数
        int symbolCount = 0;    // 记录符号出现次数

        // 遍历密码中的每个字符
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            } else if (Character.isLetter(c)) {
                letterCount++;
            } else if (isSymbol(c)) {
                symbolCount++;
            }
        }

        // 检查是否满足至少两个数字、字母和符号的要求
        if (digitCount + letterCount + symbolCount >= 2) {
            return true;
        }

        return false;
    }

    // 辅助方法，检查字符是否为键盘符号
    private boolean isSymbol(char c) {
        String symbols = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";
        return symbols.contains(String.valueOf(c));
    }

    public  void sendEmail(String emailAddress, String verifyCode) {

        try {
            HtmlEmail send = new HtmlEmail();
            //设置发送邮箱的host 默认值
            send.setHostName("smtp.qq.com");
            //配置发送邮箱和邮箱授权码
            send.setAuthentication("1613318552@qq.com","txzorcqlyixbccgj");
            //配置发送方
            send.setFrom("1613318552@qq.com");
            //配置接收人
            send.addTo(emailAddress);
            //设置邮箱主题
            send.setSubject("生猪系统邮箱验证");
            send.setSmtpPort(465);
            send.setSSLOnConnect(true);
            //具体的发送消息
            send.setMsg("欢迎使用邮箱验证本系统！");
            send.setMsg("这是您的验证码，请妥善保管:"+verifyCode);
            send.setCharset("UTF-8");
            send.send();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 生成随机验证码的方法
    private String generateRandomCode(int length) {
        // 在这里编写生成随机验证码的逻辑，例如使用随机数生成
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 生成0-9的随机数字
            codeBuilder.append(digit);
        }
        return codeBuilder.toString();
    }


}
