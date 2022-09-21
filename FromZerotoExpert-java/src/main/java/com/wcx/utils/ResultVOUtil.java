package com.wcx.utils;

import com.wcx.VO.ResultVO;
public class ResultVOUtil {
    // 返回操作成功后的信息（包括数据实体）
    public static ResultVO success(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("操作成功");
        resultVO.setData(data);
        return resultVO;
    }

    // 返回操作成功后的信息
    public static ResultVO success() {
        return ResultVOUtil.success(null);
    }

    // 返回操作失败后的信息
    public static ResultVO fail(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

}
