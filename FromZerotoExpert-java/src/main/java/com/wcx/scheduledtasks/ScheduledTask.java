package com.wcx.scheduledtasks;


import com.wcx.entity.WebsiteData;
import com.wcx.mapper.WebsiteDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScheduledTask {
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private WebsiteDataMapper websiteDataMapper;
    // 每天的最后一个时刻24点执行，将当天的网站访问数据写入mysql
    // 上网查了下,24点属于当天,0点属于第二天
    @Scheduled(cron = "0 0 24 * * ?")
    private void saveWebsiteData() {
        // 当前日期 (只有日期没有时间)
        String curDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        WebsiteData websiteData = new WebsiteData();
        websiteData.setDate(curDate);
        websiteData.setIp(redisTemplate.opsForHash().get(curDate, "ip") == null ? (Integer) redisTemplate.opsForHash().get(curDate, "ip") : 0);
        websiteData.setUv(redisTemplate.opsForHash().get(curDate, "uv") == null ? (Integer) redisTemplate.opsForHash().get(curDate, "uv") : 0);
        websiteData.setPv(redisTemplate.opsForHash().get(curDate, "pv") == null ? (Integer) redisTemplate.opsForHash().get(curDate, "pv") : 0);
        websiteDataMapper.insert(websiteData);
    }
}
