package com.wen.springcloud.loadbalance.impl;

import com.wen.springcloud.loadbalance.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class LoadBalancerImpl implements LoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private final int getAndIncrement() {
        int current;
        int next;
        /*使用自旋锁 得到期望值，如果不是期望值则一直循环直到得到想要的期望值*/
        do {
            current = this.atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0 : current +1;
        } while (!this.atomicInteger.compareAndSet(current, next));
        log.info("****第几次访问，次数next：" + next);
        return next;
    }

    /**
     * 获取注册中心服务实例
     *
     * @param serviceInstanceList
     * @return
     */
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstanceList) {
        int index = getAndIncrement() % serviceInstanceList.size();
        ServiceInstance serviceInstance = serviceInstanceList.get(index);

        return serviceInstance;
    }
}
