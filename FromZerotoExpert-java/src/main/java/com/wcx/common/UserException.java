package com.wcx.common;

import com.wcx.enums.UserExceptionEnum;

public class UserException extends RuntimeException{
    // 异常状态码
    private Integer code;


    public UserException(UserExceptionEnum userExceptionEnum) {
        super(userExceptionEnum.getMessage());
        code = userExceptionEnum.getCode();
    }

    public UserException(int code, String message) {
        super(message);
        this.code = code;
    }
}
