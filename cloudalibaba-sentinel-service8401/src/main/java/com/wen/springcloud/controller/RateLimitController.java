package com.wen.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wen.springcloud.common.CommonResult;
import com.wen.springcloud.entity.Payment;
import com.wen.springcloud.myhandler.MyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangwenge
 * @date 2021/12/16
 * @descrip 按资源名称限流 + 后续处理；按照Url地址限流 + 后续处理
 */
@RestController
@RequestMapping("/rateLimit")
@Slf4j
public class RateLimitController {
    /**
     * 按照资源byResource进行限流，来的的问题是：
     * 依照现有条件，我们自定义的处理方法又和业务代码耦合在一块，不直观
     * 每个业务方法都添加—个兜底的，那代码膨胀加剧
     * 全局统—的处理方法没有体现
     * @return
     */
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "byResourceException")
    public CommonResult byResource() {
        return new CommonResult(200, "按资源名称限流测试ok！！！！", new Payment(2021L, "serial001"));
    }

    public CommonResult byResourceException(BlockException exception) {
        return new CommonResult(500, exception.getClass().getCanonicalName() + "服务器压力太大啦,请稍后再试哦~");
    }

    /**
     * 按照请求url限流，带来的问题是不能自定义业务处理，兜底提示是sentinel默认的
     * @return
     */
    @GetMapping("/byRequestUrl")
    @SentinelResource("byRequestUrl")
    public CommonResult byRequestUrl() {
        return new CommonResult(200, "按请求url限流测试ok！！！！", new Payment(2022L, "牛哇牛哇"));
    }

    /**
     * 自定义全局异常处理/兜底服务友好提示
     * @return
     */
    @GetMapping("/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler", blockHandlerClass = MyHandler.class, blockHandler = "floodException")
    public CommonResult customerBlockHandler() {
        return new CommonResult(200, "客户自定义兜底服务友好提示", new Payment(2022L, "牛哇牛哇"));
    }
}
