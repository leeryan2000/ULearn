package com.ulearn.service.impl;

import com.ulearn.dao.PostDao;
import com.ulearn.dao.domain.*;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.*;
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


        VoteQuestion vote = postDao.getVoteQuestionByUserIdAndQuestionId(userId, form.getQuestionId());

        Integer rows;
        if (vote == null || vote.getStatus() != form.getStatus()) {
            // Just delete the item from mysql, 防止重复添加
            postDao.deleteVoteQuestionByUserIdAndQuestionId(userId, form.getQuestionId());

            vote = new VoteQuestion();
            vote.setUserId(userId);
            vote.setQuestionId(form.getQuestionId());
            vote.setStatus(form.getStatus());
            vote.setCreateTime(new Date());
            rows = postDao.voteQuestion(vote);
            if (rows != 1 ) {
                throw new CommonRuntimeException(CommonOperationError.VOTE_FAILED);
            }
        }
        else {
            rows = postDao.deleteVoteQuestionByUserIdAndQuestionId(userId, form.getQuestionId());
            if (rows != 1) {
                throw new CommonRuntimeException(CommonOperationError.VOTE_DELETE_FAILED);
            }
        }
    }

    @Override
    public void voteAnswer(Long userId, VoteAnswerForm form) {
        Answer answer = postDao.getAnswerById(form.getAnswerId());
        if (answer == null) {
            throw new CommonRuntimeException(CommonOperationError.QUESTION_DOESNT_EXIST);
        }

        VoteAnswer vote = postDao.getVoteAnswerByUserIdAndAnswerId(userId, form.getAnswerId());

        Integer rows;
        // 如果投票跟之前一样就等于删除投票
        if (vote == null || vote.getStatus() != form.getStatus()) {
            // Just delete the item from mysql, 防止重复添加
            postDao.deleteVoteAnswerByUserIdAndAnswerId(userId, form.getAnswerId());

            vote = new VoteAnswer();
            vote.setUserId(userId);
            vote.setAnswerId(form.getAnswerId());
            vote.setStatus(form.getStatus());
            vote.setCreateTime(new Date());
            rows = postDao.voteAnswer(vote);
            if (rows != 1 ) {
                throw new CommonRuntimeException(CommonOperationError.VOTE_FAILED);
            }
        }
        else {
            rows = postDao.deleteVoteAnswerByUserIdAndAnswerId(userId, form.getAnswerId());
            if (rows != 1) {
                throw new CommonRuntimeException(CommonOperationError.VOTE_DELETE_FAILED);
            }
        }
    }

    @Override
    public void addBookmark(Long userId, BookmarkForm form) {

    }

    @Override
    public void followQuestion(Long userId, FollowQuestionForm form) {

    }

    @Override
    public void followAnswer(Long userId, FollowAnswerForm form) {

    }

    @Autowired
    public PostServiceImpl(PostDao postDao) {
        this.postDao = postDao;
    }
}
