package com.ulearn.service.config;

import com.ulearn.dao.constant.PostMQConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/15 20:17
 */

public class PostMQConfig {

    @Value("${rocketmq.namesrvAddr}")
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

}
