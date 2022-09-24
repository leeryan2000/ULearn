package com.ulearn.service.config;

import cn.hutool.json.JSONUtil;
import com.ulearn.dao.FollowDao;
import com.ulearn.dao.constant.MessageConstant;
import com.ulearn.dao.constant.PostMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/15 20:17
 */

@Configuration
@Slf4j
public class PostMQConfig {

    @Value("${rocketmq.config.namesrvAddr}")
    private String namesrvAddr;

    private final FollowDao followDao;

    private final RedisTemplate<String, String> redisTemplate;

    @Bean("followMessageProducer")
    public DefaultMQProducer followMessageProducer() throws MQClientException {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer(PostMQConstant.MESSAGE_GROUP);
        // 设值NameServer地址
        producer.setNamesrvAddr(namesrvAddr);
        // 启动producer
        producer.start();

        return producer;
    }

    @Bean("followMessageConsumer")
    public DefaultMQPushConsumer followMessageConsumer() throws MQClientException {
        // 初始化一个consumer并设置
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PostMQConstant.MESSAGE_GROUP);

        consumer.setNamesrvAddr(namesrvAddr);

        consumer.subscribe(PostMQConstant.FOLLOW_MESSAGE_TOPIC, "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                // 获取消息
                MessageExt msg = list.get(0);
                String messageJsonStr = new String(msg.getBody());

                // 获取消息数据
                HashMap message = JSONUtil.toBean(messageJsonStr, HashMap.class);

                // 查看消息的类别, 查找对应关注用户
                List<Long> followerIds = new ArrayList<>();
                if (message.get(MessageConstant.MESSAGE_PROPERTY_NAME).equals(MessageConstant.FOLLOWED_QUESTION_ANSWER)) {
                    followerIds = followDao.getQuestionFollowerByQuestionId(Long.valueOf(message.get("questionId").toString()));
                }

                // 获取redis中的消息, 并添加新数据
                List<HashMap> messageList;
                for (Long followerId : followerIds) {
                    String key = "inbox_messages_" + followerId;
                    String questionFollowerListStr = redisTemplate.opsForValue().get(key);

                    if (questionFollowerListStr == null) {
                        messageList = new ArrayList<>();
                    }
                    else {
                        messageList = JSONUtil.parseArray(questionFollowerListStr).toList(HashMap.class);
                    }

                    messageList.add(message);

                    String messageListStr = JSONUtil.toJsonStr(messageList);
                    // 更新redis中的数据
                    redisTemplate.opsForValue().set(key, messageListStr);
                }

                log.info("消息成功推送, TYPE: {}, ID: {}", message.get(MessageConstant.MESSAGE_PROPERTY_NAME), message.get("answerId"));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        return consumer;
    }

    @Autowired
    public PostMQConfig(FollowDao followDao, RedisTemplate<String, String> redisTemplate) {
        this.followDao = followDao;
        this.redisTemplate = redisTemplate;
    }
}
