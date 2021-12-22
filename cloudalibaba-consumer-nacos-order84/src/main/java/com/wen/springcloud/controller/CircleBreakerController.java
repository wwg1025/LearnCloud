package com.wen.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wen.springcloud.common.CommonResult;
import com.wen.springcloud.entity.Payment;
import com.wen.springcloud.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/circleBreaker")
@Slf4j
public class CircleBreakerController {
    //支付微服务请求url
    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ConsumerService consumerService;

    /**
     * 底层使用ribbon负载均衡调用9003和9004两台微服务，sentinel服务熔断配置
     * @param id
     * @return
     */
    @GetMapping("/fallback/{id}")
    //fallback只负责业务异常 无需在sentinel控制台配置
    //@SentinelResource(value = "fallback", fallback = "handlerFallback")
    //blockHandler只负责sentinel配置违规
    //@SentinelResource(value = "fallback", blockHandler = "blockHandler")
    //1.blockHandler和fallback都配置 如果违反了sentinel限流配置规则，则被限流降级而抛出BlockException时只会进入blockHandler处理逻辑
    //2.如果没有违反sentinel配置规则，出现java运行时异常则进入fallback处理逻辑
    //exceptionsToIgnore = {IllegalArgumentException.class} 忽略异常不走fallback处理逻辑
    @SentinelResource(value = "fallback", blockHandler = "blockHandler", fallback = "handlerFallback",
    exceptionsToIgnore = {IllegalArgumentException.class})
    public CommonResult<Payment> fallback(@PathVariable("id") Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/payment/paymentSql/" + id, CommonResult.class, id);
        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常......");
        } else if (null == result.getData()) {
            throw new NullPointerException("NullPointerException,该id没有对应记录，空指针异常");
        }
        return result;
    }

    /**
     * fallback服务降级熔断兜底方法 无需在sentinel控制台配置
     * @param id
     * @param throwable
     * @return
     */
    public CommonResult<Payment> handlerFallback(@PathVariable Long id, Throwable throwable) {
        Payment payment = new Payment(id, "服务熔断");
        return new CommonResult<>(500, "支付服务开小差啦,exception内容：" + throwable.getMessage(), payment);
    }

    /**
     * blockHandler sentinel配置违规触发服务熔断降级限流 兜底服务
     * @param id
     * @param exception
     * @return
     */
    public CommonResult<Payment> blockHandler(@PathVariable Long id, BlockException exception) {
        Payment payment = new Payment(id, "服务限流");
        return new CommonResult<>(500, "blockHandler-sentinel限流,blockException内容：" + exception.getMessage(), payment);
    }

    @GetMapping("/consumerByFeign/{id}")
    public CommonResult<Payment> consumerByFeign(@PathVariable("id") Long id) {
        return consumerService.paymentSql(id);
    }
}
