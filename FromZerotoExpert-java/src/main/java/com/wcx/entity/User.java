package com.wcx.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * 用户表
 */
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userId;

    // 用户姓名
    private String userName;

    // 用户账号
    private String userAccount;

    // 用户密码
    private String userPassword;

    // 用户邮箱
    private String userEmail;

    // 用户盐值
    private String userSalt;
}
