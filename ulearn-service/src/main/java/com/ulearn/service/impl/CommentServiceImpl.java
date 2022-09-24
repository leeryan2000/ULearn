package com.ulearn.service.impl;

import com.ulearn.dao.CommentDao;
import com.ulearn.dao.domain.AnswerComment;
import com.ulearn.dao.domain.QuestionComment;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.CommentAnswerForm;
import com.ulearn.dao.form.CommentQuestionForm;
import com.ulearn.service.CommentService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

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
    public void addQuestionComment(Long userId, CommentQuestionForm form) {
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

        HashMap message = commentDao.getFollowedQuestionCommentByCommentId(questionComment.getId());


        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("followMessageProducer");
    }

    @Override
    public void addAnswerComment(Long userId, CommentAnswerForm form) {
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
    }

    @Autowired
    public CommentServiceImpl(ApplicationContext applicationContext, CommentDao commentDao) {
        this.applicationContext = applicationContext;
        this.commentDao = commentDao;
    }
}
