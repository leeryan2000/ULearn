package com.ulearn.service.impl;

import com.ulearn.dao.FollowDao;
import com.ulearn.dao.domain.AnswerFollow;
import com.ulearn.dao.domain.QuestionFollow;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.FollowAnswerForm;
import com.ulearn.dao.form.FollowQuestionForm;
import com.ulearn.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:09
 */

@Service
public class FollowServiceImpl implements FollowService {

    private final FollowDao followDao;

    @Override
    public void followQuestion(Long userId, FollowQuestionForm form) {
        // Check if the creator followed itself
        if (userId.equals(form.getUserId())) {
            throw new CommonRuntimeException(CommonOperationError.CREATOR_CANT_FOLLOW_OWN_POST);
        }

        QuestionFollow questionFollow = new QuestionFollow();

        questionFollow.setUserId(userId);
        questionFollow.setQuestionId(form.getQuestionId());
        questionFollow.setCreateTime(new Date());

        Integer rows = followDao.followQuestion(questionFollow);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.FOLLOW_FAILED);
        }
    }

    @Override
    public void followAnswer(Long userId, FollowAnswerForm form) {
        // Check if the creator followed itself
        if (userId.equals(form.getUserId())) {
            throw new CommonRuntimeException(CommonOperationError.CREATOR_CANT_FOLLOW_OWN_POST);
        }

        AnswerFollow answerFollow = new AnswerFollow();

        answerFollow.setUserId(userId);
        answerFollow.setAnswerId(form.getAnswerId());
        answerFollow.setCreateTime(new Date());

        Integer rows = followDao.followAnswer(answerFollow);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.FOLLOW_FAILED);
        }
    }

    @Autowired
    public FollowServiceImpl(FollowDao followDao) {
        this.followDao = followDao;
    }
}
