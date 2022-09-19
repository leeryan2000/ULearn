package com.ulearn.service.config;

import cn.hutool.json.JSONUtil;
import com.ulearn.dao.constant.PostMQConstant;
import com.ulearn.dao.domain.Question;
import com.ulearn.service.util.RocketMQUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/15 20:17
 */

@Configuration
public class PostMQConfig {

    @Value("${rocketmq.config.namesrvAddr}")
    private String namesrvAddr;


    @Bean("questionMessageProducer")
    public DefaultMQProducer questionMessageProducer() throws MQClientException {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer(PostMQConstant.POST_QUESTION_GROUP);
        // 设值NameServer地址
        producer.setNamesrvAddr(namesrvAddr);
        // 启动producer
        producer.start();

        return producer;
    }

    @Bean("questionMessageConsumer")
    public DefaultMQPushConsumer questionMessageConsumer() throws MQClientException {
        // 初始化一个consumer并设置
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PostMQConstant.POST_QUESTION_GROUP);

        consumer.setNamesrvAddr(namesrvAddr);

        consumer.subscribe(PostMQConstant.POST_QUESTION_TOPIC, "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                // 获取
                MessageExt msg = msgs.get(0);
                String questionJsonStr = new String(msg.getBody());
                Question question = JSONUtil.toBean(questionJsonStr, Question.class);

                

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        return consumer;
    }
}
