package com.wen.springcloud.service;

import org.springframework.cloud.stream.annotation.Output;

public interface IMessageProvider {

    String send();
}
