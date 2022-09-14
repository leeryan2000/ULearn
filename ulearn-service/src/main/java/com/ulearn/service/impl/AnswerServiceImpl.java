package com.ulearn.service.impl;

import com.ulearn.dao.AnswerDao;
import com.ulearn.dao.domain.Answer;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.AnswerForm;
import com.ulearn.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:40
 */

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerDao answerDao;

    @Override
    public void addAnswer(Long userId, AnswerForm form) {
        Answer answer = new Answer();
        answer.setUserId(userId);
        answer.setQuestionId(form.getQuestionId());
        answer.setContent(form.getContent());
        answer.setCreateTime(new Date());
        Integer rows = answerDao.addAnswer(answer);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.POST_FAILED);
        }
        // ***** 对关注问题的人进行推送

    }

    @Autowired
    public AnswerServiceImpl(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }
}
