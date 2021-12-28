package com.wen.springcloud.service.impl;

import com.wen.springcloud.dao.OrderMapper;
import com.wen.springcloud.domain.Order;
import com.wen.springcloud.service.AccountService;
import com.wen.springcloud.service.OrderService;
import com.wen.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    /**
     * 创建订单
     *
     * @param order
     */
    @Override
    @GlobalTransactional(name = "order_transactional", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("----->开始新建订单");
        //1 新建订单
        orderMapper.insert(order);

        //2 扣减库存
        log.info("----->订单微服务开始调用库存，做扣减Count");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("----->订单微服务开始调用库存，做扣减end");

        //3 扣减账户
        log.info("----->订单微服务开始调用账户，做扣减Money");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("----->订单微服务开始调用账户，做扣减end");

        //4 修改订单状态，从零到1,1代表已经完成
        log.info("----->修改订单状态开始");
        Order curOrder = new Order();
        curOrder.setId(order.getId());
        curOrder.setStatus("1");
        orderMapper.updateByPrimaryKeySelective(curOrder);
        log.info("----->修改订单状态结束");

        log.info("----->下订单结束了，O(∩_∩)O哈哈~");

    }
}
