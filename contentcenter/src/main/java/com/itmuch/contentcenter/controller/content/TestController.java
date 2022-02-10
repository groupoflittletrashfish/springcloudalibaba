package com.itmuch.contentcenter.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：liwuming
 * @date ：Created in 2022/2/8 14:25
 * @description ：
 * @modified By：
 * @version:
 */

@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 这个注入的对象就是启动类上指定的Source.class
     */
    @Autowired
    private Source source;

    @GetMapping("/test-stream")
    public String testStream() {
        //有两个发送消息，另一个是带有超时时间的消息
        this.source.output().send(MessageBuilder.withPayload("消息体").build());
        return "success";
    }
}
