package com.wen.springcloud.service;

public interface PaymentService {
    String paymentInfoOk(Integer id);

    String paymentInfoTimeout(Integer id);


    /**
     * 服务熔断
     * @param id
     * @return
     */
    String paymentCircuitBreaker(Integer id);
}
