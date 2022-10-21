package com.wcx.controller;


import com.wcx.VO.ResultVO;
import com.wcx.mapper.WebsiteDataMapper;
import com.wcx.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/websitedata")
public class WebsiteDataController {

    @Autowired
    private WebsiteDataMapper websiteDataMapper;
    @GetMapping("/getall")
    public ResultVO getAll() {
        return ResultVOUtil.success(websiteDataMapper.selectList(null));
    }
}
