package com.wcx.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("website_data")
public class WebsiteData {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String date;
    private Integer ip;
    private Integer uv;
    private Integer pv;
}
