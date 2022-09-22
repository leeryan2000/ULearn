package com.ulearn.service.impl;

import com.ulearn.dao.AnswerDao;
import com.ulearn.dao.QuestionDao;
import com.ulearn.dao.VoteDao;
import com.ulearn.dao.domain.Answer;
import com.ulearn.dao.domain.Question;
import com.ulearn.dao.domain.AnswerVote;
import com.ulearn.dao.domain.QuestionVote;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.VoteAnswerForm;
import com.ulearn.dao.form.VoteQuestionForm;
import com.ulearn.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:07
 */

@Service
public class VoteServiceImpl implements VoteService {

    private final QuestionDao questionDao;

    private final VoteDao voteDao;

    private final AnswerDao answerDao;

    @Override
    public void voteQuestion(Long userId, VoteQuestionForm form) {
        Question question = questionDao.getQuestionById(form.getQuestionId());
        if (question == null) {
            throw new CommonRuntimeException(CommonOperationError.QUESTION_DOESNT_EXIST);
        }

        QuestionVote vote = voteDao.getQuestionVoteByUserIdAndQuestionId(userId, form.getQuestionId());

        Integer rows;
        if (vote == null || vote.getStatus() != form.getStatus()) {
            // Just delete the item from mysql, 防止重复添加
            voteDao.deleteQuestionVoteByUserIdAndQuestionId(userId, form.getQuestionId());

            vote = new QuestionVote();
            vote.setUserId(userId);
            vote.setQuestionId(form.getQuestionId());
            vote.setStatus(form.getStatus());
            vote.setCreateTime(new Date());
            rows = voteDao.voteQuestion(vote);
            if (rows != 1 ) {
                throw new CommonRuntimeException(CommonOperationError.VOTE_FAILED);
            }
        }
        else {
            rows = voteDao.deleteQuestionVoteByUserIdAndQuestionId(userId, form.getQuestionId());
            if (rows != 1) {
                throw new CommonRuntimeException(CommonOperationError.VOTE_DELETE_FAILED);
            }
        }
    }

    @Override
    public void voteAnswer(Long userId, VoteAnswerForm form) {
        Answer answer = answerDao.getAnswerById(form.getAnswerId());
        if (answer == null) {
            throw new CommonRuntimeException(CommonOperationError.QUESTION_DOESNT_EXIST);
        }

        AnswerVote vote = voteDao.getAnswerVoteByUserIdAndAnswerId(userId, form.getAnswerId());

        Integer rows;
        // 如果投票跟之前一样就等于删除投票
        if (vote == null || vote.getStatus() != form.getStatus()) {
            // Just delete the item from mysql, 防止重复添加
            voteDao.deleteAnswerVoteByUserIdAndAnswerId(userId, form.getAnswerId());

            vote = new AnswerVote();
            vote.setUserId(userId);
            vote.setAnswerId(form.getAnswerId());
            vote.setStatus(form.getStatus());
            vote.setCreateTime(new Date());
            rows = voteDao.voteAnswer(vote);
            if (rows != 1 ) {
                throw new CommonRuntimeException(CommonOperationError.VOTE_FAILED);
            }
        }
        else {
            rows = voteDao.deleteAnswerVoteByUserIdAndAnswerId(userId, form.getAnswerId());
            if (rows != 1) {
                throw new CommonRuntimeException(CommonOperationError.VOTE_DELETE_FAILED);
            }
        }
    }

    @Autowired
    public VoteServiceImpl(QuestionDao questionDao, VoteDao voteDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.voteDao = voteDao;
        this.answerDao = answerDao;
    }
}
