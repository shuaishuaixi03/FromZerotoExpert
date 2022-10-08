package com.wcx.forms;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginForm {
    // 登录账号
    @NotBlank(message = "登录账号不能为空")
    private String loginAccount;
    // 登录密码
    @NotBlank(message = "登录密码不能为空")
    private String loginPassword;
}
