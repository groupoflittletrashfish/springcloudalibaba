package com.itmuch.usercenter.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * @author ：liwuming
 * @date ：Created in 2022/2/8 16:07
 * @description ：
 * @modified By：
 * @version:
 */
@Service
@Slf4j
public class TestStreamConsumer {

    @StreamListener(Sink.INPUT)
    public void receive(String messsage) {
        log.info("通过stream收到的消息:{}", messsage);
    }
}
