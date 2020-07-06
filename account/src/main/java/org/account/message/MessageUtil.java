package org.account.message;

import com.google.common.collect.Maps;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Map;

@EnableBinding(value = {Processor.class, Consumer.class})
public class MessageUtil {

    @Resource
    private Processor processor;

    public void sendMessage() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("test", "123456");
        map.put("test123", "123456");
        try {
            System.out.println("消息发送时间: " + Instant.now().toString());
            processor.output().send(MessageBuilder.withPayload(map)
                    .setHeader("x-delay", 10000).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(Sink.INPUT)
    public void distributorInput(Map<String, Object> message) {
        System.out.println(message);
        System.out.println("消息接收时间: " + Instant.now().toString());
        throw new RuntimeException("失败消息测试");
    }

}
