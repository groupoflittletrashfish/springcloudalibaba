package com.itmuch.usercenter.rocketmq;

import com.itmuch.usercenter.dao.bonus.BonusEventLogMapper;
import com.itmuch.usercenter.dao.user.UserMapper;
import com.itmuch.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.itmuch.usercenter.domain.entity.bonus.BonusEventLog;
import com.itmuch.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * consumerGroup 这个属性也是自定义的，只是用于定于该消费者所在的组别
 *
 *
 * @author ：liwuming
 * @date ：Created in 2022/1/24 16:04
 * @description ：
 * @modified By：
 * @version:
 */

//@Service
@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "add-bonus")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {

    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;

    @Override
    public void onMessage(UserAddBonusMsgDTO message) {
        //为用户增加积分
        Integer userId = message.getUserId();
        User user = this.userMapper.selectByPrimaryKey(userId);
        user.setBonus(user.getBonus() + message.getBonus());
        this.userMapper.updateByPrimaryKeySelective(user);

        //记录日志到bonus_event_log表中
        this.bonusEventLogMapper.insert(BonusEventLog.builder().userId(userId).value(message.getBonus())
                .event("CONTRIBUTE").createTime(new Date()).description("投稿加积分...").build());
    }
}
