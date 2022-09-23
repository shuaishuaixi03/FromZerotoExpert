package com.wcx.controller;


import com.wcx.VO.ResultVO;
import com.wcx.service.DisallowWordService;
import com.wcx.utils.ResultVOUtil;
import com.wcx.utils.Trie;
import com.wcx.utils.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DisallowWordController {
    @Autowired
    private DisallowWordService disallowWordService;

    /** 根据新的敏感词表更新前缀树 */
    @GetMapping("/disallowword/updateTrie")
    public ResultVO updateTrie() throws Exception {
        List<String> words = disallowWordService.getALlWordValue();
        if (words == null) {
            throw new Exception("敏感词表数据为空");
        }
        ValidUtil.setTrie(Trie.constructTrie(words.toArray(new String[words.size()])));
        return ResultVOUtil.success(null);
    }
}
