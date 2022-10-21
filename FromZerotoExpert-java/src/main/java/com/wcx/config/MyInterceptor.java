package com.wcx.config;

import com.alibaba.fastjson.JSONObject;
import com.wcx.forms.WebsiteDataForm;
import com.wcx.service.Websocket;
import com.wcx.utils.IPUtil;
import com.wcx.utils.MyTimeUtil;
import com.wcx.utils.ResultVOUtil;
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
    //返回值为false，则说明禁止访问
    //返回值为true，则说明允许访问
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //不拦截路径（登录路径等等）
        List<String> asList = Arrays.asList("/fromzerotoexpert/register", "/fromzerotoexpert/login");
        String uri = request.getRequestURI();
        //1.设置放行路径
        if(asList.contains(uri)){
            return true;
        }
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
            } else {
                response.getWriter().write(JSONObject.toJSONString(ResultVOUtil.fail(401,"你的账号已在其它地方登录")));
                return false;
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
    // 更新网站当日的访问数据并向前端发送通知
    private void countWebsiteData(HttpServletRequest request) {
        // 当前日期 (只有日期没有时间)
        String curDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int newIpCount = (int) redisTemplate.opsForHash().get(curDate, "ip");
        int newUvCount = (int) redisTemplate.opsForHash().get(curDate, "uv");
        int newPvCount = (int) redisTemplate.opsForHash().get(curDate, "pv");
        // 调用IPUtil类的getIpAdress()方法获取请求真正的ip地址
        String curIp = IPUtil.getIpAdrress(request);
        // 如果是个新的ip地址，更新ip集合和newIp的值
        if (!redisTemplate.opsForSet().isMember("ip", curIp)) {
            redisTemplate.opsForSet().add("ip", curIp);
            newIpCount ++;
        }
        // 获取当前时间到第二天凌晨时间的秒数
        long targetSeconds = MyTimeUtil.getNowToNextDaySeconds();
        // 获取请求中Cookie键为UID的值
        String UID = String.valueOf(getCookie(request, "UID"));
        if (redisTemplate.opsForSet().isMember("uv", UID)) {
        } else {
            Cookie cookie = new Cookie("UID", request.getRemoteHost() + System.currentTimeMillis());
            // 设置第二天凌晨过期
            cookie.setMaxAge((int) targetSeconds);
            newUvCount ++;
        }
        newPvCount ++;
        redisTemplate.opsForHash().put(curDate, "ip", newIpCount);
        redisTemplate.opsForHash().put(curDate, "uv", newUvCount);
        redisTemplate.opsForHash().put(curDate, "pv", newPvCount);
        // 把过期时间设置长一些，以便定时统计
        redisTemplate.expire(curDate, targetSeconds + 5 * 60, TimeUnit.SECONDS);
        WebsiteDataForm websiteDataForm = new WebsiteDataForm();
        websiteDataForm.setDate(curDate);
        websiteDataForm.setIp(newIpCount);
        websiteDataForm.setUv(newUvCount);
        websiteDataForm.setPv(newPvCount);
        // 向前端发送新的序列化后的网站访问数据
        Websocket.sendMessage(JSONObject.toJSONString(websiteDataForm));
    }
}

