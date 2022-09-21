package com.wcx.forms;

import lombok.Data;

@Data
public class RegisterForm {
    // 用户姓名
    private String userName;

    // 用户账号
    private String userAccount;

    // 用户密码
    private String userPassword;

    // 用户邮箱
    private String userEmail;
}
