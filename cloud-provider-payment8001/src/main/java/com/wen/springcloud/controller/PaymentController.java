package com.wen.springcloud.controller;

import com.wen.springcloud.common.CommonResult;
import com.wen.springcloud.entity.Payment;
import com.wen.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wwg
 * @descrip 支付业务逻辑处理类
 */
@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/create")
    public CommonResult<?> create(@RequestBody @Validated Payment payment) {
        CommonResult commonResult = new CommonResult();
        int result = paymentService.create(payment);
        commonResult.setCode(200);
        commonResult.setMessage("成功了,调用的端口号：" + serverPort);
        commonResult.setData(result);
        return commonResult;
    }


    @GetMapping("/getPaymentById/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        CommonResult commonResult = new CommonResult();
        Payment result = paymentService.getPaymentById(id);
        commonResult.setCode(200);
        commonResult.setMessage("成功了,调用的端口号：" + serverPort);
        commonResult.setData(result);
        return commonResult;
    }


    @GetMapping("/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("服务:{}" , service);
        }
        List<ServiceInstance> instanceList = discoveryClient.getInstances("COLUD-PAYMENT-SERVICE");
        for (ServiceInstance serviceInstance : instanceList) {
            log.info(serviceInstance.getServiceId() +"\t" + serviceInstance.getHost() + "\t" +
                    serviceInstance.getPort() + "\t" + serviceInstance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping("/getServerPort")
    public String getServerPort() {
        return serverPort;
    }

    @GetMapping("/paymentFeignTimeOut")
    public String paymentFeignTimeOut() {
        try {
            synchronized (this) {
                this.wait(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
