package com.ulearn.service.util;

import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.error.CommonSystemError;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/19 13:07
 */

@Slf4j
public class RocketMQUtil {

    public static void syncSendMsg(DefaultMQProducer producer, Message msg) throws Exception{
        SendResult result = producer.send(msg);
        if (!(result.getSendStatus() == SendStatus.SEND_OK)) {
            throw new CommonRuntimeException(CommonSystemError.MQ_FAILED_SENDMESSAGE);
        }
        log.info("Message sent successfully");
    }

    // 异步发送消息, 加入 new SendCallBack() {}
    public static void asyncSendMsg(DefaultMQProducer producer, Message msg) throws Exception{
        producer.send(msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步发送消息成功，消息id：" + sendResult.getMsgId());
            }
            @Override
            public void onException(Throwable e) {
                e.printStackTrace();
            }
        });
    }
}
