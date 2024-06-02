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
import com.ulearn.dao.error.CommonError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.error.CommonSystemError;
import com.ulearn.service.util.PostRedisUtil;
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

    private final PostRedisUtil postRedisUtil;


    @Bean("messageProducer")
    public DefaultMQProducer messageProducer() throws MQClientException {
        // Initialize producer and set Producer group name
        DefaultMQProducer producer = new DefaultMQProducer(PostMQConstant.MESSAGE_GROUP);
        // set NameServer address
        producer.setNamesrvAddr(namesrvAddr);
        // start producer
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

                try {
                    MessageExt msg = list.get(0);
                    String messageJsonStr = new String(msg.getBody());

                    // Turn json message to class Answer
                    Answer answer = JSONUtil.toBean(messageJsonStr, Answer.class);
                    // could retrieve the information by the answer id

                    // Send the new answer to users that follows the question
                    HashMap message = answerDao.getQuestionAnswerByAnswerId(answer.getId());
                    message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.QUESTION_ANSWER);
                    postRedisUtil.addMessageByUserId(Long.valueOf(message.get("questionUserId").toString()), message);

                    // Add message to redis by the followers' user ID
                    message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.FOLLOWED_QUESTION_ANSWER);
                    List<Long> followerIds = followDao.getQuestionFollowerByQuestionId(answer.getQuestionId());
                    for (Long followerId : followerIds) {
                        postRedisUtil.addMessageByUserId(followerId, message);
                    }

                    log.info("Answer message delivered successfully, ID: {}", answer.getId());
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

            }
        });

        consumer.start();

        return consumer;
    }


    @Bean("questionCommentMessageConsumer")
    public DefaultMQPushConsumer questionCommentMessageConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PostMQConstant.QUESTION_COMMENT_MESSAGE_GROUP);

        consumer.setNamesrvAddr(namesrvAddr);

        consumer.subscribe(PostMQConstant.MESSAGE_TOPIC, PostMQConstant.QUESTION_COMMENT_MESSAGE_TAG);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    MessageExt msg = list.get(0);
                    String messageJsonStr = new String(msg.getBody());

                    // Turn json message to class QuestionComment
                    QuestionComment questionComment = JSONUtil.toBean(messageJsonStr, QuestionComment.class);

                    // Send the comment message to the user who posted the question
                    HashMap message = commentDao.getQuestionCommentMessageById(questionComment.getId());
                    message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.QUESTION_COMMENT);
                    postRedisUtil.addMessageByUserId(Long.valueOf(message.get("questionUserId").toString()), message);

                    // Add message to redis by the followers' ID
                    message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.FOLLOWED_QUESTION_COMMENT);
                    List<Long> followerIds = followDao.getQuestionFollowerByQuestionId(questionComment.getQuestionId());
                    for (Long followerId : followerIds) {
                        postRedisUtil.addMessageByUserId(followerId, message);
                    }

                    log.info("Question comment message delivered successfully, ID: {}", questionComment.getId());
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });

        consumer.start();

        return consumer;
    }

    @Bean("answerCommentMessageConsumer")
    public DefaultMQPushConsumer answerCommentMessageConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PostMQConstant.ANSWER_COMMENT_MESSAGE_GROUP);

        consumer.setNamesrvAddr(namesrvAddr);

        consumer.subscribe(PostMQConstant.MESSAGE_TOPIC, PostMQConstant.ANSWER_COMMENT_MESSAGE_TAG);
        // consumer.setConsumeThreadMax();

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    MessageExt msg = list.get(0);
                    String messageJsonStr = new String(msg.getBody());

                    // Turn json message to class AnswerComment
                    AnswerComment answerComment = JSONUtil.toBean(messageJsonStr, AnswerComment.class);

                    // Send the answer's comment message to the user who posted the answer
                    HashMap message = commentDao.getAnswerCommentMessageById(answerComment.getId());
                    message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.ANSWER_COMMENT);
                    postRedisUtil.addMessageByUserId(Long.valueOf(message.get("answerUserId").toString()), message);

                    // Add message to redis byt the followers' ID
                    message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.FOLLOWED_ANSWER_COMMENT);
                    List<Long> followerIds = followDao.getAnswerFollowerByAnswerId(answerComment.getAnswerId());
                    for (Long followerId : followerIds) {
                        postRedisUtil.addMessageByUserId(followerId, message);
                    }

                    log.info("Answer comment message delivered successfully, ID: {}", answerComment.getId());
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });

        consumer.start();

        return consumer;
    }

    @Autowired
    public PostMQConfig(FollowDao followDao, AnswerDao answerDao, CommentDao commentDao, PostRedisUtil postRedisUtil) {
        this.followDao = followDao;
        this.answerDao = answerDao;
        this.commentDao = commentDao;
        this.postRedisUtil = postRedisUtil;
    }
}
