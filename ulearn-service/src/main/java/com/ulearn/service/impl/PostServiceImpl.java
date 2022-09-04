package com.ulearn.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.ulearn.dao.PostDao;
import com.ulearn.dao.domain.Answer;
import com.ulearn.dao.domain.Question;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.AnswerForm;
import com.ulearn.dao.form.QuestionForm;
import com.ulearn.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 17:07
 */

@Service
public class PostServiceImpl implements PostService {

    private final PostDao postDao;

    @Override
    public void addQuestion(QuestionForm form, Long userId) {
        Question question = new Question();
        question.setUserId(userId);
        question.setTitle(form.getTitle());
        question.setContent(form.getContent());
        question.setCreateTime(new Date());
        Integer rows = postDao.addQuestion(question);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.POST_FAILED);
        }
    }

    @Override
    public void addAnswer(AnswerForm form, Long userId) {
        Answer answer = new Answer();
        answer.setUserId(userId);
        answer.setQuestionId(form.getQuestionId());
        answer.setContent(form.getContent());
        answer.setCreateTime(new Date());
        Integer rows = postDao.addAnswer(answer);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.POST_FAILED);
        }
        // ***** 对关注问题的人进行推送
    }

    @Autowired
    public PostServiceImpl(PostDao postDao) {
        this.postDao = postDao;
    }
}
