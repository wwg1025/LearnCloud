package com.wen.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wen.springcloud.common.CommonResult;

/**
 * @author wangwenge
 * @date 2021/12/16
 * @descrip 自定义全局异常兜底服务处理器
 */
public class MyHandler {
    public static CommonResult powerCutException(BlockException exception) {
        return new CommonResult(500, "停电咯，工作人员正在加急抢修，请耐心等待哦");
    }
    public static CommonResult floodException(BlockException exception) {
        return new CommonResult(500, "发大水咯，服务器都被冲跑了，小哥正在奔跑捡回，请耐心等待哦");
    }
}
