package com.wcx.enums;


import lombok.Getter;

@Getter
public enum UserExceptionEnum {
    NAME_IS_NULL(101, "用户昵称为空"),
    ACCOUNT_IS_NULL(102, "用户账号为空"),
    PASSWORD_IS_NULL(103, "用户密码为空")
    ;


    private Integer code;
    private String message;

    UserExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
