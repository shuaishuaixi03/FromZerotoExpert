package com.wcx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcx.entity.User;
import com.wcx.mapper.UserMapper;
import com.wcx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getOneByName(String name) {
//        if (name == null) {
//            throw new
//        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", name));
        return user;
    }

    @Override
    public User getOneByAccount(String account) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", account));
        return user;
    }

    @Override
    public Integer addOne(User user) {
       int res = userMapper.insert(user);
       return res;
    }
}
