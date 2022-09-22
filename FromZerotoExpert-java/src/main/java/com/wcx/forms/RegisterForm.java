package com.wcx.forms;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class RegisterForm {
    // 用户姓名
    private String userName;

    // 用户账号
    private String userAccount;

    // 用户密码
    private String userPassword;

    // 用户邮箱
    @Email(message = "邮箱格式不正确")
    private String userEmail;
}
