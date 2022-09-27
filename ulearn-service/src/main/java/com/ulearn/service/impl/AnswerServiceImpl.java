package com.ulearn.service.impl;

import cn.hutool.json.JSONUtil;
import com.ulearn.dao.AnswerDao;
import com.ulearn.dao.FollowDao;
import com.ulearn.dao.constant.MessageConstant;
import com.ulearn.dao.constant.PostMQConstant;
import com.ulearn.dao.domain.Answer;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.AnswerForm;
import com.ulearn.service.AnswerService;
import com.ulearn.service.util.RocketMQUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:40
 */

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerDao answerDao;

    private final ApplicationContext applicationContext;

    @Override
    @Transactional
    public void addAnswer(Long userId, AnswerForm form) throws Exception {
        Answer answer = new Answer();
        answer.setUserId(userId);
        answer.setQuestionId(form.getQuestionId());
        answer.setContent(form.getContent());
        answer.setCreateTime(new Date());
        Integer rows = answerDao.addAnswer(answer);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.POST_FAILED);
        }

        // 通过消息队列给追踪的用户发送提醒
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("messageProducer");
        String messageJsonStr = JSONUtil.toJsonStr(answer);
        Message msg = new Message(PostMQConstant.ANSWER_MESSAGE_TOPIC, messageJsonStr.getBytes());
        RocketMQUtil.syncSendMsg(producer, msg);
    }

    @Autowired
    public AnswerServiceImpl(AnswerDao answerDao, ApplicationContext applicationContext) {
        this.answerDao = answerDao;
        this.applicationContext = applicationContext;
    }
}
