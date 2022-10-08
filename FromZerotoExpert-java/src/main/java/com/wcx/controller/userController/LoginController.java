package com.wcx.controller.userController;
import com.wcx.VO.ResultVO;
import com.wcx.entity.User;
import com.wcx.forms.LoginForm;
import com.wcx.service.UserService;
import com.wcx.utils.PasswordUtil;
import com.wcx.utils.ResultVOUtil;
import com.wcx.utils.SessionIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Validated
public class LoginController {
    @Autowired
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public ResultVO login(@RequestBody @Valid LoginForm loginForm, BindingResult bindingResult,
                          HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.fail(100, "登录参数不正确");
        }
        User user = userService.getOneByAccount(loginForm.getLoginAccount());
        if (user == null) {
            return ResultVOUtil.fail(101, "账号不存在");
        }
        String entryptPassword = PasswordUtil.getPBKDF2(loginForm.getLoginPassword(), user.getUserSalt());
        if (!user.getUserPassword().equals(entryptPassword)) {
            return ResultVOUtil.fail(102, "密码错误");
        }
        String sessionId = SessionIdUtil.createSessionId(loginForm.getLoginAccount());
        session.setMaxInactiveInterval(60 * 60 * 24);
        return ResultVOUtil.success("登录");
    }
}
