package com.wcx.controller.userController;

import com.wcx.VO.ResultVO;
import com.wcx.entity.User;
import com.wcx.forms.RegisterForm;
import com.wcx.service.UserService;
import com.wcx.utils.PasswordUtil;
import com.wcx.utils.ResultVOUtil;
import com.wcx.utils.ValidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@Validated
@Component
public class RegisterController {
    private static UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        RegisterController.userService = userService;
    }

    @PostMapping("/register")
    private ResultVO register(@RequestBody @Valid RegisterForm registerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.fail(1,"注册参数不正确");
        }
        if (!ValidUtil.isVaildName(registerForm.getUserName())) {
            log.error("昵称不符合");
            return ResultVOUtil.fail(2, "昵称不符合");
        }
        if (!ValidUtil.isVaildAccount(registerForm.getUserAccount())) {
            log.error("账号不符合");
            return ResultVOUtil.fail(3, "账号不符合");
        }
        if (!ValidUtil.isVaildPassword(registerForm.getUserPassword())) {
            log.error("密码不符合");
            return ResultVOUtil.fail(4, "密码不符合");
        }
        if (userService.getOneByName(registerForm.getUserName()) != null) {
            log.error("昵称已存在");
            return ResultVOUtil.fail(5, "昵称已存在");
        }
        if (userService.getOneByAccount(registerForm.getUserAccount()) != null) {
            log.error("账号已存在");
            return ResultVOUtil.fail(6, "账号已存在");
        }
        // 密码加密
        String salt = PasswordUtil.salt();
        String entryptPassword = PasswordUtil.getPBKDF2(
                registerForm.getUserPassword(),
                salt
        );
        registerForm.setUserPassword(entryptPassword);
        User user = new User();
        BeanUtils.copyProperties(registerForm, user);
        user.setUserSalt(salt);
        userService.addOne(user);
        return ResultVOUtil.success();
    }
}
