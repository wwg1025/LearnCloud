package com.wen.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.wen.springcloud.dao")
public class MyBatisConfig {

}
