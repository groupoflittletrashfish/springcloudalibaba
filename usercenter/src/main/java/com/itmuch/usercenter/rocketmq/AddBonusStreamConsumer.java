package com.itmuch.usercenter.rocketmq;

import com.itmuch.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.itmuch.usercenter.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * @author ：liwuming
 * @date ：Created in 2022/2/9 16:07
 * @description ：
 * @modified By：
 * @version:
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusStreamConsumer {

    private final UserService userService;

    /**
     * 接收事务性消息
     * @param message
     */
    @StreamListener(Sink.INPUT)
    public void receive(UserAddBonusMsgDTO message) {
        //这个就是业务逻辑，没什么好讲的
        userService.addBonus(message);
    }
}
