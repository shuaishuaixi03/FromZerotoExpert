package com.wcx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcx.entity.DisallowWord;
import com.wcx.mapper.DisallowWordMapper;
import com.wcx.service.DisallowWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DisallowWordServiceImpl extends ServiceImpl<DisallowWordMapper, DisallowWord> implements DisallowWordService {
    @Autowired
    private DisallowWordMapper disallowWordMapper;

    @Override
    public List<String> getALlWordValue() {
        List<DisallowWord> disallowWordList = disallowWordMapper.selectList(new QueryWrapper<DisallowWord>().isNotNull("word_id"));
        return disallowWordList.stream().map(disallowWord -> disallowWord.getWordValue()).collect(Collectors.toList());
    }
}
