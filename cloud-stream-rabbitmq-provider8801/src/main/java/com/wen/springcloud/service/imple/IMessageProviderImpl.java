package com.wen.springcloud.service.imple;

import com.wen.springcloud.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;
import java.util.UUID;
//定义消息的推送通道
@EnableBinding(Source.class)
public class IMessageProviderImpl implements IMessageProvider {
    //消息发送通道
    @Resource
    private MessageChannel output;

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("serial=======" + serial);
        return serial;
    }
}
