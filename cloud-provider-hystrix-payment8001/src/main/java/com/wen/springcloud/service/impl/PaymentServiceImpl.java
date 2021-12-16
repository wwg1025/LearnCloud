package com.wen.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wen.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymentInfoOk(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo ok, id：" + id + "O(∩_∩)O哈哈~";
    }

    /*服务降级策略 超时线程 超时3秒钟则使用兜底方法作友好提示*/
    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @Override
    public String paymentInfoTimeout(Integer id) {
        int timeout = 5;
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo time out, id：" + id + "O(∩_∩)O哈哈~ 耗时" + timeout + "秒钟";
    }

    /**
     * 服务熔断
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerHandler", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求阈值 （请求次数）
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") //失败率达到多少后跳闸
    })
    @Override
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("id不能为负数");
        }
        String serialNum = IdUtil.simpleUUID();


        return Thread.currentThread().getName() + "调用成功，流水号：" +serialNum;
    }

    private String paymentCircuitBreakerHandler(Integer id) {
        return "指令输入错误，请重新输入再试或者等待几秒钟再试 o(╥﹏╥)o id：" + id;
    }


    public String paymentInfoTimeoutHandler(Integer id) {

        return "线程池：" + Thread.currentThread().getName() + " 系统繁忙，请稍后再试，id：" + id + "o(╥﹏╥)o";
    }





}
