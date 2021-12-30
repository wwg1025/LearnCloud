package com.wen.springcloud;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//取消数据源的自动创建，而是使用自己定义的
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
@EnableDiscoveryClient
public class SeataOrderMainApp2001 {
    /**
     * 启动服务之前先启动nacos、再启动seata TC 端 window命令：seata-server.bat -p 8091 -h 127.0.0.1 -m db
     * 使用nacos作为配置中心需要将config.txt（包括server端和client端）配置文件上送到nacos 使用以下命令
     * sh nacos-config.sh -h 47.102.97.183 -p 8848 -g DEFAULT_GROUP -t e039ecd9-d1bb-490b-bc32-e8a5459bd89c -u nacos -w nacos
     * -h -p 指定nacos的端口地址；
     * -g 指定配置的分组，注意，是配置的分组；
     * -t 指定命名空间id；
     * -u -w指定nacos的用户名和密码，开启了nacos注册和配置认证的才需要指定。
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SeataOrderMainApp2001.class, args);
    }
}
