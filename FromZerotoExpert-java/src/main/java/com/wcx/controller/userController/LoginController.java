package com.wcx.controller.userController;
import com.wcx.VO.ResultVO;
import com.wcx.entity.User;
import com.wcx.forms.LoginForm;
import com.wcx.service.UserService;
import com.wcx.utils.AccountIdUtil;
import com.wcx.utils.PasswordUtil;
import com.wcx.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

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
        // 根据登录密码生成加密密码
        String entryptPassword = PasswordUtil.getPBKDF2(loginForm.getLoginPassword(), user.getUserSalt());
        if (!user.getUserPassword().equals(entryptPassword)) {
            return ResultVOUtil.fail(102, "密码错误");
        }
        // 取得会话的sessionId
        String sessionId = session.getId();
        // 根据账号生成accountId
        String accountId = AccountIdUtil.createAccountId(loginForm.getLoginAccount());
        // 将accountId 与 sessionId 绑定
        redisTemplate.opsForValue().set(accountId, sessionId);
        redisTemplate.expire(accountId, 60*60*24, TimeUnit.SECONDS);
        Cookie cookie = new Cookie("accountId",accountId);
        response.addCookie(cookie);
        return ResultVOUtil.success("登录");
    }
    @GetMapping("/isLogin")
    public ResultVO isLogin(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("accountId")) {
                    String accountId = cookie.getValue();
                    String sessionId = (String) redisTemplate.opsForValue().get(accountId);
                    if (sessionId == null) {
                        return ResultVOUtil.fail(430, "请重新登录");
                    }
                    if (sessionId.equals(request.getSession().getId())) {
                        return ResultVOUtil.success();
                    }
                }
            }
        }
        return ResultVOUtil.fail(430, "请重新登录");
    }
}
