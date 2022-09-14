package com.wcx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class testController {
    @GetMapping("/FromZerotoExpert")
    public String Test01(HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie myCookie = null;
        // 注意判断cookies是否指向空指针, 否则当浏览器没有cookie时，会抛出空指针异常
        if (cookies != null) {
            // 遍历cookies，找到对应的cookie
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("wcx")) {
                    myCookie = cookie;
                    break;
                }
            }
        }
        if (myCookie != null) {
            return "Hello, Welcome Again To From Zero To Expert";
        } else {
            Cookie newCookie = new Cookie("wcx", "001");
            // 设置newCookie的存活时间为24小时
            newCookie.setMaxAge(24 * 3600);
            response.addCookie(newCookie);
            return "Hi, Welcome To From Zero To Expert";
        }
    }

}
