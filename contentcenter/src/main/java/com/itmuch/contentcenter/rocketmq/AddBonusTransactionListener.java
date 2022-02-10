package com.itmuch.contentcenter.rocketmq;

import com.alibaba.fastjson.JSON;
import com.itmuch.contentcenter.dao.messaging.RocketmqTransactionLogMapper;
import com.itmuch.contentcenter.domain.dto.content.ShareAuditDTO;
import com.itmuch.contentcenter.domain.entity.messaging.RocketmqTransactionLog;
import com.itmuch.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Objects;

/**
 * 事务型消息的监听类
 *
 * @author ：liwuming
 * @date ：Created in 2022/2/7 16:38
 * @description ：
 * @modified By：
 * @version:
 */
@RocketMQTransactionListener(txProducerGroup = "mygroup")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {

    //业务类，可无视
    private final ShareService shareService;
    //业务Mapper,可无视
    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

    /**
     * 这个函数的主要作用是操作本地的事务，如果本地事务成功则提交，反之回滚
     *
     * @param message
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        //可以获取消息的头部信息
        MessageHeaders headers = message.getHeaders();

        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer shareId = Integer.valueOf((String) Objects.requireNonNull(headers.get("share_id")));

        String dtoString = (String)headers.get("dto");
        ShareAuditDTO auditDTO = JSON.parseObject(dtoString, ShareAuditDTO.class);

        try {
            //这个也是业务逻辑，代码就不贴出来了，就是将 用户的积分添加日志插入到数据库
            this.shareService.auditByIdWithRocketMqLog(shareId, auditDTO, transactionId);
            //如果成功则提交
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            //反之回滚
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 这个函数是一个回查，即在一些特殊情况下，那么可以根据自定义的需求来进行表的回查，通俗来讲就是有一些未知原因的消息，不确实是提交还是回滚，那么通过回查函数来确定
     *
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        MessageHeaders headers = message.getHeaders();

        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        //业务逻辑：查询 积分日志表，如果表里已经有数据了，则提交，反之回滚
        RocketmqTransactionLog rocketmqTransactionLog = rocketmqTransactionLogMapper.selectOne(RocketmqTransactionLog.builder().transactionId(transactionId).build());
        if (rocketmqTransactionLog != null) {
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
