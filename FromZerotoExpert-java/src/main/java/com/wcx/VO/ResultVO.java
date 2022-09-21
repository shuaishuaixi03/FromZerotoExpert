package com.wcx.VO;

import lombok.Data;

@Data
public class ResultVO {
    // 状态码
    private Integer code;
    // 提示信息
    private String msg;
    // 数据实体
    private Object data;
}
