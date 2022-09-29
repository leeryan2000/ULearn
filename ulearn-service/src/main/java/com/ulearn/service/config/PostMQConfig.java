package com.ulearn.service.config;

import cn.hutool.json.JSONUtil;
import com.ulearn.dao.AnswerDao;
import com.ulearn.dao.CommentDao;
import com.ulearn.dao.FollowDao;
import com.ulearn.dao.constant.MessageConstant;
import com.ulearn.dao.constant.PostMQConstant;
import com.ulearn.dao.domain.Answer;
import com.ulearn.dao.domain.AnswerComment;
import com.ulearn.dao.domain.QuestionComment;
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

    private final CommentDao commentDao;

    private final MessageRedisUtil messageRedisUtil;

    private final RedisTemplate<String, String> redisTemplate;

    @Bean("answerMessageProducer")
    public DefaultMQProducer answerMessageProducer() throws MQClientException {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer(PostMQConstant.ANSWER_MESSAGE_GROUP);
        // 设值NameServer地址
        producer.setNamesrvAddr(namesrvAddr);
        // 启动producer
        producer.start();

        return producer;
    }

    @Bean("answerMessageConsumer")
    public DefaultMQPushConsumer answerMessageConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PostMQConstant.ANSWER_MESSAGE_GROUP);

        consumer.setNamesrvAddr(namesrvAddr);

        consumer.subscribe(PostMQConstant.MESSAGE_TOPIC, PostMQConstant.ANSWER_MESSAGE_TAG);

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

                log.info("Answer 消息成功推送, ID: {}", answer.getId());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        return consumer;
    }

    @Bean("questionCommentMessageProducer")
    public DefaultMQProducer questionCommentMessageProducer() throws MQClientException {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer(PostMQConstant.QUESTION_COMMENT_MESSAGE_GROUP);
        // 设值NameServer地址
        producer.setNamesrvAddr(namesrvAddr);
        // 启动producer
        producer.start();

        return producer;
    }

    @Bean("questionCommentMessageConsumer")
    public DefaultMQPushConsumer questionCommentMessageConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PostMQConstant.QUESTION_COMMENT_MESSAGE_GROUP);

        consumer.setNamesrvAddr(namesrvAddr);

        consumer.subscribe(PostMQConstant.MESSAGE_TOPIC, PostMQConstant.QUESTION_COMMENT_MESSAGE_TAG);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                // 获取消息
                MessageExt msg = list.get(0);
                String messageJsonStr = new String(msg.getBody());

                // 获取消息数据
                QuestionComment questionComment = JSONUtil.toBean(messageJsonStr, QuestionComment.class);

                // 给发布问题的用户发送新回答消息
                HashMap message = commentDao.getQuestionCommentMessageById(questionComment.getId());
                message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.QUESTION_COMMENT);
                messageRedisUtil.addMessageByUserId(Long.valueOf(message.get("questionUserId").toString()), message);

                // 获取redis中的消息, 并添加新数据
                message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.FOLLOWED_QUESTION_COMMENT);
                List<Long> followerIds = followDao.getQuestionFollowerByQuestionId(questionComment.getQuestionId());
                for (Long followerId : followerIds) {
                    messageRedisUtil.addMessageByUserId(followerId, message);
                }

                log.info("Question comment 消息成功推送, ID: {}", questionComment.getId());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        return consumer;
    }

    @Bean("answerCommentMessageProducer")
    public DefaultMQProducer answerCommentMessageProducer() throws MQClientException {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer(PostMQConstant.ANSWER_COMMENT_MESSAGE_GROUP);
        // 设值NameServer地址
        producer.setNamesrvAddr(namesrvAddr);
        // 启动producer
        producer.start();

        return producer;
    }

    @Bean("answerCommentMessageConsumer")
    public DefaultMQPushConsumer answerCommentMessageConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PostMQConstant.ANSWER_COMMENT_MESSAGE_GROUP);

        consumer.setNamesrvAddr(namesrvAddr);

        consumer.subscribe(PostMQConstant.MESSAGE_TOPIC, PostMQConstant.ANSWER_COMMENT_MESSAGE_TAG);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                // 获取消息
                MessageExt msg = list.get(0);
                String messageJsonStr = new String(msg.getBody());

                // 获取消息数据
                AnswerComment answerComment = JSONUtil.toBean(messageJsonStr, AnswerComment.class);

                // 给发布问题的用户发送新回答消息
                HashMap message = commentDao.getAnswerCommentMessageById(answerComment.getId());
                message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.ANSWER_COMMENT);
                messageRedisUtil.addMessageByUserId(Long.valueOf(message.get("answerUserId").toString()), message);

                // 获取redis中的消息, 并添加新数据
                message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.FOLLOWED_ANSWER_COMMENT);
                List<Long> followerIds = followDao.getAnswerFollowerByAnswerId(answerComment.getAnswerId());
                for (Long followerId : followerIds) {
                    messageRedisUtil.addMessageByUserId(followerId, message);
                }

                log.info("Answer comment 消息成功推送, ID: {}", answerComment.getId());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        return consumer;
    }

    @Autowired
    public PostMQConfig(FollowDao followDao, AnswerDao answerDao, CommentDao commentDao, MessageRedisUtil messageRedisUtil, RedisTemplate<String, String> redisTemplate) {
        this.followDao = followDao;
        this.answerDao = answerDao;
        this.commentDao = commentDao;
        this.messageRedisUtil = messageRedisUtil;
        this.redisTemplate = redisTemplate;
    }
}
