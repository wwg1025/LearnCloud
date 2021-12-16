package com.wen.springcloud.service;

import com.wen.springcloud.common.CommonResult;
import com.wen.springcloud.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "colud-payment-service")
@Component
public interface PaymentFeignService {

    /**
     * 服务接口绑定 调用服务提供者提供的这个方法
     * @param id
     * @return
     */
    @GetMapping("/payment/getPaymentById/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping("/payment/paymentFeignTimeOut")
    String paymentFeignTimeOut();

}
