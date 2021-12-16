package com.wen.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wen.springcloud.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/orderHystrix")
@Slf4j
@DefaultProperties(defaultFallback = "defaultGlobalFallbackHandler")
public class OrderHystrixController {

    @Resource
    private OrderService orderService;

    @GetMapping("/consumer/hystrix/paymentInfoOk/{id}")
    public String paymentInfoOk(@PathVariable("id") Integer id) {
        return orderService.paymentInfoOk(id);
    }

    /*@HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })*/
    @HystrixCommand
    @GetMapping("/consumer/hystrix/paymentInfoTimeout/{id}")
    public String paymentInfoTimeout(@PathVariable("id") Integer id) {

        return orderService.paymentInfoTimeout(id);
    }

    public String paymentInfoTimeoutHandler(Integer id) {
        return "我是消费者80，对方支付系统繁忙请稍后再试o(╥﹏╥)o";
    }

    public String defaultGlobalFallbackHandler() {
        return "defaultGlobalFallback 业务繁忙请稍后再试！o(╥﹏╥)o";
    }


    @GetMapping("/consumer/hystrix/paymentCircuitBreaker/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        return orderService.paymentCircuitBreaker(id);
    }
}
