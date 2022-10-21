package com.wcx.config;

import com.alibaba.fastjson.JSONObject;
import com.wcx.service.Websocket;
import com.wcx.utils.IPUtil;
import com.wcx.utils.MyTimeUtil;
import com.wcx.utils.ResultVOUtil;
import com.wcx.utils.WebsiteDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.springframework.web.util.WebUtils.getCookie;


@Component
public class MyInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private WebsiteDataUtil websiteDataUtil;


    //返回值为false，则说明禁止访问
    //返回值为true，则说明允许访问
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //不拦截路径（登录路径等等）
        List<String> asList = Arrays.asList("/fromzerotoexpert/register", "/fromzerotoexpert/login","/fromzerotoexpert/news/send");

        String uri = request.getRequestURI();
        //1.设置放行路径
        if(asList.contains(uri)){
            return true;
        }

        // 当前日期
        String curDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));


        // pv + 1
        int pv = 0;
        if (redisTemplate.opsForHash().get(curDate, "pv") != null) {
            pv = (int) redisTemplate.opsForHash().get(curDate, "pv");
        }
        redisTemplate.opsForHash().put(curDate, "pv", pv + 1);

        // ip + 1
        // 获取客户端的真实ip地址
        String ipAdress = IPUtil.getIpAdrress(request);
        if (!redisTemplate.opsForSet().isMember("ip", ipAdress)) {
            redisTemplate.opsForSet().add("ip", ipAdress);
            int ip = 0;
            if (redisTemplate.opsForHash().get(curDate, "ip") != null) {
                ip = (int) redisTemplate.opsForHash().get(curDate, "ip");
            }
            redisTemplate.opsForHash().put(curDate, "ip", ip + 1);
            redisTemplate.expire("ip", MyTimeUtil.getNowToNextDaySeconds(), TimeUnit.SECONDS);
        }
        redisTemplate.expire(curDate, MyTimeUtil.getNowToNextDaySeconds(), TimeUnit.SECONDS);


        System.out.println(websiteDataUtil.getWebsiteData().toString());
        Websocket.sendMessage(websiteDataUtil.getWebsiteData());


        // 设置输出流编码格式，支持中文
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (getCookie(request, "accountId") != null) {
            Cookie cookie = getCookie(request, "accountId");
            String accountId = cookie.getValue();
            String sessionId = (String) redisTemplate.opsForList().index(accountId, 0);
            if (sessionId == null) {
                response.getWriter().write(JSONObject.toJSONString(ResultVOUtil.fail(401,"请先登录")));
                return false;
            }
            if (sessionId.equals(request.getSession().getId())) {
                return true;
            }
        }
//        System.out.println(JSONObject.toJSONString(ResultVOUtil.fail(401,"请先登录")));
        response.getWriter().write(JSONObject.toJSONString(ResultVOUtil.fail(401,"请先登录")));
        System.out.println("方法执行前.......");
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("方法执行后.......");
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("清理.......");
    }
}

