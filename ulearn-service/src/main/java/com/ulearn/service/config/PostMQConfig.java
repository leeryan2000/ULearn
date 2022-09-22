package com.ulearn.service.config;

import cn.hutool.json.JSONUtil;
import com.ulearn.dao.FollowDao;
import com.ulearn.dao.QuestionDao;
import com.ulearn.dao.constant.MessageConstant;
import com.ulearn.dao.constant.PostMQConstant;
import com.ulearn.dao.domain.Answer;
import com.ulearn.dao.domain.User;
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

    @Bean("answerMessageProducer")
    public DefaultMQProducer questionMessageProducer() throws MQClientException {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer(PostMQConstant.POST_QUESTION_GROUP);
        // 设值NameServer地址
        producer.setNamesrvAddr(namesrvAddr);
        // 启动producer
        producer.start();

        return producer;
    }

    @Bean("answerMessageConsumer")
    public DefaultMQPushConsumer questionMessageConsumer() throws MQClientException {
        // 初始化一个consumer并设置
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PostMQConstant.POST_QUESTION_GROUP);

        consumer.setNamesrvAddr(namesrvAddr);

        consumer.subscribe(PostMQConstant.POST_QUESTION_TOPIC, "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                // 获取消息
                MessageExt msg = list.get(0);
                String answerJsonStr = new String(msg.getBody());
                Answer answer = JSONUtil.toBean(answerJsonStr, Answer.class);
                // 获取消息数据
                HashMap message = followDao.getFollowedQuestionAnswerByAnswerId(answer.getId());

                // 通过问题ID获取问题关注列表
                List<Long> questionFollowerIds = followDao.getQuestionFollowerByQuestionId(answer.getQuestionId());
                System.out.println("Question follower: " + questionFollowerIds.size());

                List<HashMap> messageList;
                for (Long questionFollowerId : questionFollowerIds) {
                    String key = "inbox_messages_" + questionFollowerId;
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
