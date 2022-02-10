package com.itmuch.usercenter.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

/**
 * @author ：liwuming
 * @date ：Created in 2022/2/9 11:29
 * @description ：
 * @modified By：
 * @version:
 */

@Service
@Slf4j
public class MyTestStreamConsumer {

    @StreamListener(MySink.MY_INPUT)
    public void receive(String message) {
        log.info("通过stream收到了消息：{}", message);
//        throw new RuntimeException("手动触发异常来验证全局异常处理是否生效");
    }


    /**
     * 全局异常处理
     */
    @StreamListener("errorChannel")
    public void error(Message message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        log.error("发生异常，errorMsg = {}", errorMessage);
    }
}
