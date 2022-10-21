package com.wcx.controller.userController;
import com.wcx.VO.ResultVO;
import com.wcx.entity.User;
import com.wcx.forms.LoginForm;
import com.wcx.forms.UserForm;
import com.wcx.service.UserService;
import com.wcx.utils.*;
import org.springframework.beans.BeanUtils;
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
import static org.springframework.web.util.WebUtils.getCookie;

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
        redisTemplate.opsForList().remove(accountId, 0, 0);
        redisTemplate.opsForList().rightPushAll(accountId, sessionId, System.currentTimeMillis());
        redisTemplate.expire(accountId, 60*60*24, TimeUnit.SECONDS);
        Cookie cookie = new Cookie("accountId",accountId);
        response.addCookie(cookie);
        UserForm userForm = new UserForm();
        BeanUtils.copyProperties(user, userForm);
        return ResultVOUtil.success(userForm);
    }
//    @GetMapping("/isLogin")
//    public ResultVO isLogin(HttpServletRequest request) {
//        if (getCookie(request, "accountId") != null) {
//            Cookie cookie = getCookie(request, "accountId");
//            String accountId = cookie.getValue();
//            String sessionId = (String) redisTemplate.opsForList().index(accountId, 0);
//            if (sessionId == null) {
//                return ResultVOUtil.fail(430, "请重新登录");
//            }
//            if (sessionId.equals(request.getSession().getId())) {
//                return ResultVOUtil.success();
//            }
//        }
//        return ResultVOUtil.fail(430, "请重新登录");
//    }
    @GetMapping("/logout")
    public ResultVO logout(HttpServletRequest request) {
//        if (isLogin(request).getCode() != 0) {
//            return ResultVOUtil.fail(431, "请先登录");
//        }
        String accountId = getCookie(request, "accountId").getValue();
        redisTemplate.opsForList().remove(accountId, 0, 0);
        return ResultVOUtil.success("退出登录成功");
    }
}
