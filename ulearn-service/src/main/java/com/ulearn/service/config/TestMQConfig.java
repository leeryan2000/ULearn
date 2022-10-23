package com.ulearn.service.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/10/18 18:20
 */

@Configuration
@Slf4j
public class TestMQConfig {

    @Value("${rocketmq.config.namesrvAddr}")
    private String namesrvAddr;

    @Bean("TEST_PRODUCER")
    public DefaultMQProducer producerTest() throws MQClientException {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer("TEST_PRODUCER_GROUP");
        // 设值NameServer地址
        producer.setNamesrvAddr(namesrvAddr);
        // 启动producer
        producer.start();

        return producer;
    }

    @Bean
    public DefaultMQPushConsumer consumerTest1() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TEST_CONSUMER_GROUP_1");

        consumer.setNamesrvAddr(namesrvAddr);

        consumer.subscribe("TEST_TOPIC_1", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                // 获取消息
                MessageExt msg = list.get(0);
                String messageJsonStr = new String(msg.getBody());


                log.info("TEST_TOPIC_1 接受到消息 {}", messageJsonStr);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        return consumer;
    }

    @Bean
    public DefaultMQPushConsumer consumerTest2() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TEST_CONSUMER_GROUP_2");

        consumer.setNamesrvAddr(namesrvAddr);

        consumer.subscribe("TEST_TOPIC_2", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                // 获取消息
                MessageExt msg = list.get(0);
                String messageJsonStr = new String(msg.getBody());

                log.info("TEST_TOPIC_2 接受到消息 {}", messageJsonStr);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        return consumer;
    }
}
