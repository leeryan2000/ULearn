package com.ulearn.service.impl;

import cn.hutool.crypto.digest.mac.MacEngine;
import cn.hutool.json.JSONUtil;
import com.ulearn.dao.QuestionDao;
import com.ulearn.dao.constant.PostMQConstant;
import com.ulearn.dao.domain.*;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.*;
import com.ulearn.service.QuestionService;
import com.ulearn.service.util.RocketMQUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 17:07
 */

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    private final ApplicationContext applicationContext;

    @Transactional
    @Override
    public void addQuestion(Long userId, QuestionForm form) throws Exception {
        Question question = new Question();

        question.setUserId(userId);
        question.setTitle(form.getTitle());
        question.setContent(form.getContent());
        question.setCreateTime(new Date());

        Integer rows = questionDao.addQuestion(question);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.POST_FAILED);
        }

        // 添加问题标签
        List<Long> tags = form.getTags();
        QuestionTag questionTag;
        for (Long tagId : tags) {
            questionTag = new QuestionTag();

            questionTag.setQuestionId(question.getId());
            questionTag.setTagId(tagId);

            rows = questionDao.addQuestionTag(questionTag);
            if (rows != 1) {
                throw new CommonRuntimeException(CommonOperationError.QUESTION_TAG_ADD_FAILED);
            }
        }

        // 通过消息队列给追踪的用户发送提醒
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("questionMessageProducer");
        String questionJsonStr = JSONUtil.toJsonStr(question);
        Message msg = new Message(PostMQConstant.POST_QUESTION_TOPIC, questionJsonStr.getBytes());
        RocketMQUtil.asyncSendMsg(producer, msg);
    }



    @Autowired
    public QuestionServiceImpl(QuestionDao questionDao, ApplicationContext applicationContext) {
        this.questionDao = questionDao;
        this.applicationContext = applicationContext;
    }
}
