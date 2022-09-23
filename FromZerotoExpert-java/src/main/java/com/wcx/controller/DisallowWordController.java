package com.wcx.controller;


import com.wcx.VO.ResultVO;
import com.wcx.service.DisallowWordService;
import com.wcx.utils.ResultVOUtil;
import com.wcx.utils.Trie;
import com.wcx.utils.ValidUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disallowword")
public class DisallowWordController {
    @Autowired
    private DisallowWordService disallowWordService;

    /** 根据新的敏感词表重构前缀树 */
    @GetMapping("/reconstructTrie")
    public ResultVO updateTrie() throws Exception {
        List<String> words = disallowWordService.getALlWordValue();
        if (words == null) {
            throw new Exception("敏感词表数据为空");
        }
        ValidUtil.setTrie(Trie.constructTrie(words.toArray(new String[words.size()])));
        return ResultVOUtil.success(null);
    }

    /** 向前缀树中插入新单词 */
    @PostMapping("/addTrie")
    public ResultVO addTrie(@Param("disallowword") String disallowword) throws Exception {
        if (disallowword == null) {
            throw new Exception("敏感词为空");
        }
        ValidUtil.getTrie().insert(disallowword);
        return ResultVOUtil.success(null);
    }
    @PostMapping("/deleteTrie")
    public ResultVO deleteTrie(@Param("disallowword") String disallowword) throws Exception {
        if (disallowword == null) {
            throw new Exception("敏感词为空");
        }
        ValidUtil.getTrie().delete(disallowword);
        return ResultVOUtil.success(null);
    }

}
