package com.ulearn.service.impl;

import com.ulearn.dao.FollowDao;
import com.ulearn.dao.domain.FollowAnswer;
import com.ulearn.dao.domain.FollowQuestion;
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

        FollowQuestion followQuestion = new FollowQuestion();

        followQuestion.setUserId(userId);
        followQuestion.setQuestionId(form.getQuestionId());
        followQuestion.setCreateTime(new Date());

        Integer rows = followDao.followQuestion(followQuestion);
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

        FollowAnswer followAnswer = new FollowAnswer();

        followAnswer.setUserId(userId);
        followAnswer.setAnswerId(form.getAnswerId());
        followAnswer.setCreateTime(new Date());

        Integer rows = followDao.followAnswer(followAnswer);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.FOLLOW_FAILED);
        }
    }

    @Autowired
    public FollowServiceImpl(FollowDao followDao) {
        this.followDao = followDao;
    }
}
