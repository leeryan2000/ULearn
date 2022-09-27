package com.ulearn.service.impl;

import cn.hutool.json.JSONUtil;
import com.ulearn.dao.CommentDao;
import com.ulearn.dao.constant.MessageConstant;
import com.ulearn.dao.constant.PostMQConstant;
import com.ulearn.dao.domain.AnswerComment;
import com.ulearn.dao.domain.QuestionComment;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.CommentAnswerForm;
import com.ulearn.dao.form.CommentQuestionForm;
import com.ulearn.service.CommentService;
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
 * @Date: 2022/9/23 14:45
 */

@Service
public class CommentServiceImpl implements CommentService {

    private final ApplicationContext applicationContext;

    private final CommentDao commentDao;

    @Override
    @Transactional
    public void addQuestionComment(Long userId, CommentQuestionForm form) throws Exception {
        QuestionComment questionComment = new QuestionComment();
        questionComment.setQuestionId(form.getQuestionId());
        questionComment.setUserId(userId);
        questionComment.setContent(form.getContent());
        questionComment.setReplyId(form.getReplyId());
        questionComment.setCreateTime(new Date());

        Integer rows = commentDao.addQuestionComment(questionComment);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.COMMENT_FAILED);
        }

        // 获取数据
        HashMap message = commentDao.getQuestionCommentByCommentId(questionComment.getId());
        // 设置消息类型
        message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.FOLLOWED_QUESTION_COMMENT);

        // 通过消息队列给追踪的用户发送提醒
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("messageProducer");
        String messageJsonStr = JSONUtil.toJsonStr(message);
        Message msg = new Message(PostMQConstant.FOLLOW_POST_MESSAGE_TOPIC, messageJsonStr.getBytes());
        RocketMQUtil.syncSendMsg(producer, msg);
    }

    @Override
    @Transactional
    public void addAnswerComment(Long userId, CommentAnswerForm form) throws Exception {
        AnswerComment answerComment = new AnswerComment();
        answerComment.setAnswerId(form.getAnswerId());
        answerComment.setUserId(userId);
        answerComment.setContent(form.getContent());
        answerComment.setReplyId(form.getReplyId());
        answerComment.setCreateTime(new Date());

        Integer rows = commentDao.addAnswerComment(answerComment);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.COMMENT_FAILED);
        }

        // 获取数据
        HashMap message = commentDao.getAnswerCommentByCommentId(answerComment.getId());
        // 设置消息类型
        message.put(MessageConstant.MESSAGE_PROPERTY_NAME, MessageConstant.FOLLOWED_ANSWER_COMMENT);

        // 通过消息队列给追踪的用户发送提醒
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("messageProducer");
        String messageJsonStr = JSONUtil.toJsonStr(message);
        Message msg = new Message(PostMQConstant.FOLLOW_POST_MESSAGE_TOPIC, messageJsonStr.getBytes());
        RocketMQUtil.syncSendMsg(producer, msg);
    }

    @Autowired
    public CommentServiceImpl(ApplicationContext applicationContext, CommentDao commentDao) {
        this.applicationContext = applicationContext;
        this.commentDao = commentDao;
    }
}
