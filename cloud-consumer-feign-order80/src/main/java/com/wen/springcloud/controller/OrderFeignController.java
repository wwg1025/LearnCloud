package com.wen.springcloud.controller;

import com.wen.springcloud.common.CommonResult;
import com.wen.springcloud.entity.Payment;
import com.wen.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/orderFeign")
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/getPaymentById/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }


    @GetMapping("/consumer/paymentFeignTimeOut")
    public String paymentFeignTimeOut() {
        return paymentFeignService.paymentFeignTimeOut();
    }

}
