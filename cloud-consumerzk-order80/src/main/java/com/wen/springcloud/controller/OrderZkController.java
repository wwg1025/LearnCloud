package com.wen.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("orderzk")
@Slf4j
public class OrderZkController {

    /**
     * 服务提供者调用地址 订单支付
     */
    private static final String INVOKE_URL = "http://colud-payment-service";

    @Resource
    private RestTemplate restTemplate;


    @GetMapping("/paymentInfo")
    public String paymentInfo() {
        String result = restTemplate.getForObject(INVOKE_URL + "/payment/zk", String.class);

        return result;
    }


}
