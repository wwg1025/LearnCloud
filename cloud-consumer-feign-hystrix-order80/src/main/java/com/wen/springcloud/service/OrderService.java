package com.wen.springcloud.service;

import com.wen.springcloud.service.impl.OrderServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "cloud-provider-hystrix-payment", fallback = OrderServiceImpl.class)
public interface OrderService {

    @GetMapping("/paymentHystrix/hystrix/paymentInfoOk/{id}")
    String paymentInfoOk(@PathVariable("id") Integer id);

    @GetMapping("/paymentHystrix/hystrix/paymentInfoTimeout/{id}")
    String paymentInfoTimeout(@PathVariable("id") Integer id);

    @GetMapping("/paymentHystrix/hystrix/paymentCircuitBreaker/{id}")
    String paymentCircuitBreaker(@PathVariable("id") Integer id);
}
