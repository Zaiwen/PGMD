package com.example.swinedatebaseproject.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterDTO {
    private String name;

    private String password;

    private String mail;

    @NotBlank(message = "验证码不能为空")
    private String code; // 验证码
}
