package com.wen.springcloud.controller;

import com.wen.springcloud.common.CommonResult;
import com.wen.springcloud.entity.Payment;
import com.wen.springcloud.loadbalance.LoadBalancer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wwg
 * @descrip 订单业务逻辑处理类
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    //public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://colud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/getPaymentById/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/getPaymentById/" + id, CommonResult.class);
    }

    @GetMapping("/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> responseEntity = restTemplate.getForEntity(PAYMENT_URL + "/payment/getPaymentById/" + id, CommonResult.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return new CommonResult<>(444, "操作失败");
        }
    }

    @GetMapping("/getPaymentLB")
    public String getPaymentLB() {
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("colud-payment-service");
        if (null == serviceInstanceList || serviceInstanceList.size() <= 0) {
            return null;
        }

        ServiceInstance serviceInstance = loadBalancer.instances(serviceInstanceList);
        String result = restTemplate.getForObject(serviceInstance.getUri() + "/payment/getServerPort", String.class);

        return result;
    }
}
