package com.wen.springcloud.loadbalance;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {

    /**
     * 获取注册中心服务实例
     * @param serviceInstanceList
     * @return
     */
    ServiceInstance instances(List<ServiceInstance> serviceInstanceList);
}
