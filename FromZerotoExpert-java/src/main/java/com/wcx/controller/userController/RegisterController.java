package com.wcx.controller.userController;

import com.wcx.VO.ResultVO;
import com.wcx.entity.User;
import com.wcx.forms.RegisterForm;
import com.wcx.service.UserService;
import com.wcx.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    private ResultVO register(@RequestBody RegisterForm registerForm) {
        if (userService.getOneByName(registerForm.getUserName()) != null) {
            log.error("昵称已存在");
            return ResultVOUtil.fail(2, "昵称已存在");
        }
        if (userService.getOneByAccount(registerForm.getUserAccount()) != null) {
            log.error("账号已存在");
            return ResultVOUtil.fail(3, "账号已存在");
        }
        User user = new User();
        BeanUtils.copyProperties(registerForm, user);
        userService.addOne(user);
        return ResultVOUtil.success();
    }
}
