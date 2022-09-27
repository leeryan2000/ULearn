package com.ulearn.service.config;

import cn.hutool.json.JSONUtil;
import com.ulearn.dao.AnswerDao;
import com.ulearn.dao.FollowDao;
import com.ulearn.dao.constant.MessageConstant;
import com.ulearn.dao.constant.PostMQConstant;
import com.ulearn.dao.domain.Answer;
import com.ulearn.service.util.MessageRedisUtil;
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

    private final AnswerDao answerDao;

    private final MessageRedisUtil messageRedisUtil;

    private final RedisTemplate<String, String> redisTemplate;

    @Bean("messageProducer")
    public DefaultMQProducer messageProducer() throws MQClientException {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer(PostMQConstant.MESSAGE_GROUP);
        // 设值NameServer地址
        producer.setNamesrvAddr(namesrvAddr);
        // 启动producer
        producer.start();

        return producer;
    }

    @Bean("answerMessageConsumer")
    public DefaultMQPushConsumer answerMessageConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PostMQConstant.MESSAGE_GROUP);

        consumer.setNamesrvAddr(namesrvAddr);

        consumer.subscribe(PostMQConstant.ANSWER_MESSAGE_TOPIC, "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                // 获取消息
                MessageExt msg = list.get(0);
                String messageJsonStr = new String(msg.getBody());

                // 获取消息数据
                Answer answer = JSONUtil.toBean(messageJsonStr, Answer.class);

                // 给发布问题的用户发送新回答消息
                HashMap message = answerDao.getQuestionAnswerByAnswerId(answer.getId());
                message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.QUESTION_ANSWER);
                messageRedisUtil.addMessageByUserId(Long.valueOf(message.get("questionUserId").toString()), message);

                // 获取redis中的消息, 并添加新数据
                message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.FOLLOWED_QUESTION_ANSWER);
                List<Long> followerIds = followDao.getQuestionFollowerByQuestionId(answer.getQuestionId());
                for (Long followerId : followerIds) {
                    messageRedisUtil.addMessageByUserId(followerId, message);
                }

                log.info("消息成功推送, TYPE: {}", message.get(MessageConstant.MESSAGE_PROPERTY_NAME));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        return consumer;
    }

    @Autowired
    public PostMQConfig(FollowDao followDao, AnswerDao answerDao, MessageRedisUtil messageRedisUtil, RedisTemplate<String, String> redisTemplate) {
        this.followDao = followDao;
        this.answerDao = answerDao;
        this.messageRedisUtil = messageRedisUtil;
        this.redisTemplate = redisTemplate;
    }
}
