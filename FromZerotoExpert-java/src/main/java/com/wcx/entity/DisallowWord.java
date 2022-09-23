package com.wcx.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 敏感词表
 */
@Data
@TableName("disallow_word")
public class DisallowWord {

    @TableId(type = IdType.AUTO)
    private Integer wordId;

    //敏感词值
    private String wordValue;

    //敏感词类型
    private Integer wordType;
}
