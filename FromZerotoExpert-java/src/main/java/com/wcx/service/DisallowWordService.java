package com.wcx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wcx.entity.DisallowWord;

import java.util.List;

public interface DisallowWordService extends IService<DisallowWord> {

    /** 查询敏感词表中所有敏感词的值 */
    List<String> getALlWordValue();
}
