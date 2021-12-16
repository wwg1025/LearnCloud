package com.wen.springcloud.service.impl;

import com.wen.springcloud.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public String paymentInfoOk(Integer id) {
        return "支付成功模块业务已下线，不好意思哈~";
    }

    @Override
    public String paymentInfoTimeout(Integer id) {
        return "支付超时模块业务已下线，不好意思哈~";
    }

    @Override
    public String paymentCircuitBreaker(Integer id) {
        return "服务熔断模块已下线，不好意思哈~";
    }
}
