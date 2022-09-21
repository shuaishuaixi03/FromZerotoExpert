package com.wcx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wcx.entity.User;

public interface UserService extends IService<User> {
    // 根据用户昵称查询用户信息
    User getOneByName(String name);
    // 根据用户账号查询用户信息
    User getOneByAccount(String account);

    // 添加用户信息
    Integer addOne(User user);
}
