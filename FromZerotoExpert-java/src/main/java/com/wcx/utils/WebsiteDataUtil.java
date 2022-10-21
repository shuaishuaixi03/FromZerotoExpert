package com.wcx.utils;

import com.alibaba.fastjson.JSONObject;
import com.wcx.forms.WebsiteDataForm;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class WebsiteDataUtil {
    @Resource
    private RedisTemplate redisTemplate;


    public String getWebsiteData() {
        WebsiteDataForm websiteDataForm = new WebsiteDataForm();
        // 当前日期
        String curDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        websiteDataForm.setDate(curDate);
        websiteDataForm.setUv(getWebsiteDataValue("uv"));
        websiteDataForm.setPv(getWebsiteDataValue("pv"));
        websiteDataForm.setIp(getWebsiteDataValue("ip"));
        return JSONObject.toJSONString(websiteDataForm);
    }
    private Integer getWebsiteDataValue(String target) {
        int res = 0;
        // 当前日期
        String curDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if (redisTemplate.opsForHash().get(curDate, target) != null)
            res = (int) redisTemplate.opsForHash().get(curDate, target);
        return res;
    }
}
