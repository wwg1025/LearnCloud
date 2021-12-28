package com.wen.springcloud.controller;

import com.wen.springcloud.domain.CommonResult;
import com.wen.springcloud.domain.Order;
import com.wen.springcloud.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 测试get url示例：http://localhost:2001/order/create?userId=1&productId=1&count=10&money=100
     * @param order
     * @return
     */
    @GetMapping("/create")
    public CommonResult create(Order order) {
        orderService.create(order);
        return new CommonResult(200, "订单创建成功");
    }
}
