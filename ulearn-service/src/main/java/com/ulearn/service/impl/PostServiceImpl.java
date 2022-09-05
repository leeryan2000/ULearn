package com.ulearn.service.impl;

import com.ulearn.dao.PostDao;
import com.ulearn.dao.domain.Answer;
import com.ulearn.dao.domain.Question;
import com.ulearn.dao.domain.VoteAnswer;
import com.ulearn.dao.domain.VoteQuestion;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.AnswerForm;
import com.ulearn.dao.form.QuestionForm;
import com.ulearn.dao.form.VoteAnswerForm;
import com.ulearn.dao.form.VoteQuestionForm;
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
    public void addQuestion(Long userId, QuestionForm form) {
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
    public void addAnswer(Long userId, AnswerForm form) {
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

    @Override
    public void voteQuestion(Long userId, VoteQuestionForm form) {
        Question question = postDao.getQuestionById(form.getQuestionId());
        if (question == null) {
            throw new CommonRuntimeException(CommonOperationError.QUESTION_DOESNT_EXIST);
        }

        // Just delete the item from mysql, 防止重复添加
        postDao.deleteVoteQuestionByUserIdAndQuestionId(userId, form.getQuestionId());

        VoteQuestion vote = postDao.getVoteQuestionByUserIdAndQuestionId(userId, form.getQuestionId());

        if (vote == null || vote.getStatus() != form.getStatus()) {
            vote = new VoteQuestion();
            vote.setUserId(userId);
            vote.setQuestionId(form.getQuestionId());
            vote.setStatus(form.getStatus());
            vote.setCreateTime(new Date());
            Integer rows = postDao.voteQuestion(vote);
            if (rows != 1 ) {
                throw new CommonRuntimeException(CommonOperationError.VOTE_FAILED);
            }
        }
    }

    @Override
    public void voteAnswer(Long userId, VoteAnswerForm form) {
        Answer answer = postDao.getAnswerById(form.getAnswerId());
        if (answer == null) {
            throw new CommonRuntimeException(CommonOperationError.QUESTION_DOESNT_EXIST);
        }

        // Just delete the item from mysql, 防止重复添加
        postDao.deleteVoteAnswerByUserIdAndAnswerId(userId, form.getAnswerId());

        VoteAnswer vote = postDao.getVoteAnswerByUserIdAndAnswerId(userId, form.getAnswerId());

        // 如果投票跟之前一样就等于删除投票
        if (vote == null || vote.getStatus() != form.getStatus()) {
            vote = new VoteAnswer();
            vote.setUserId(userId);
            vote.setAnswerId(form.getAnswerId());
            vote.setStatus(form.getStatus());
            vote.setCreateTime(new Date());
            Integer rows = postDao.voteAnswer(vote);
            if (rows != 1 ) {
                throw new CommonRuntimeException(CommonOperationError.VOTE_FAILED);
            }
        }
    }

    @Autowired
    public PostServiceImpl(PostDao postDao) {
        this.postDao = postDao;
    }
}
