package com.wen.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wwg
 * @descrip 通过配置编码的方式实现网关路由+过滤
 */
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator getRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("payment_route3",
                predicateSpec -> predicateSpec.path("/mil")
                        .uri("http://news.baidu.com/mil")).build();

        return routes.build();
    }

}
