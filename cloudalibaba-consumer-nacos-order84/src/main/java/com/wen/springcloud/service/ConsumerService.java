package com.wen.springcloud.service;

import com.wen.springcloud.common.CommonResult;
import com.wen.springcloud.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @descrip feign声明式调用服务
 */
@FeignClient(value = "nacos-payment-provider", fallback = FallBackService.class)
public interface ConsumerService {

    /**
     * 调用支付微服务方法 注意 mapping url 需同支付微服务请求url一致
     * @param id
     * @return
     */
    @GetMapping("/payment/paymentSql/{id}")
    CommonResult<Payment> paymentSql(@PathVariable("id") Long id);
}
