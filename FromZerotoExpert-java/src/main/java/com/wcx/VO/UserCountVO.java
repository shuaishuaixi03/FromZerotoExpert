package com.wcx.VO;

import lombok.Data;

@Data
public class UserCountVO {
    // 登录人数
    private Integer loginCount;
    // 实时在线人数
    private Integer realtimeCount;
}
