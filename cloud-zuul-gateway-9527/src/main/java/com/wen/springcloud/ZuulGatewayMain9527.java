package com.wen.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulGatewayMain9527 {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayMain9527.class, args);
    }
}
