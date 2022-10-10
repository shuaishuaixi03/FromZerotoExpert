package com.wcx.controller;


import com.wcx.VO.ResultVO;
import com.wcx.VO.UserCountVO;
import com.wcx.utils.ResultVOUtil;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

@RestController
public class StatisticsController {
    @Resource
    private RedisTemplate redisTemplate;
    @GetMapping("/getUserCount")
    public ResultVO getUserCount() {
        UserCountVO userCountVO = new UserCountVO();
        Set<String> keys = redisTemplate.keys("*");
        int loginCount = 0, realtimeCount = 0;
        for (String key : keys) {
            if (redisTemplate.type(key) == DataType.LIST) {
                loginCount ++;
                long oldTime = (long) redisTemplate.opsForList().index(key, 1);
                if (System.currentTimeMillis() -  oldTime < 10000) {
                    realtimeCount ++;
                }
            }
        }
        userCountVO.setLoginCount(loginCount);
        userCountVO.setRealtimeCount(realtimeCount);
        return ResultVOUtil.success(userCountVO);
    }
}
