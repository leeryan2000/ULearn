package com.ulearn.service.impl;

import cn.hutool.json.JSONUtil;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void voteQuestion(Long userId, VoteQuestionForm form) {
        Question question = questionDao.getQuestionById(form.getQuestionId());
        if (question == null) {
            throw new CommonRuntimeException(CommonOperationError.QUESTION_DOESNT_EXIST);
        }

        // Get the vote data from redis first
        String key = "question_vote";
        String fieldKey =  userId + "-" + form.getQuestionId();

        // delete the item from MySQL if it exists to prevent from reinsertion
        voteDao.deleteQuestionVoteByUserIdAndQuestionId(userId, form.getQuestionId());
        redisTemplate.opsForHash().delete(key, fieldKey);

        QuestionVote vote = new QuestionVote();
        vote.setUserId(userId);
        vote.setQuestionId(form.getQuestionId());
        vote.setStatus(form.getStatus());
        vote.setCreateTime(new Date());

        // add to redis first, and use quartz schedule to store it into MySQL
        redisTemplate.opsForHash().put(key, fieldKey, JSONUtil.toJsonStr(vote));
    }

    @Override
    public void voteAnswer(Long userId, VoteAnswerForm form) {
        Answer answer = answerDao.getAnswerById(form.getAnswerId());
        if (answer == null) {
            throw new CommonRuntimeException(CommonOperationError.QUESTION_DOESNT_EXIST);
        }

        // Get the vote data from redis first
        String key = "answer_vote";
        String fieldKey =  userId + "-" + form.getAnswerId();

        // delete the item from MySQL if it exists to prevent from reinsertion
        voteDao.deleteAnswerVoteByUserIdAndAnswerId(userId, form.getAnswerId());
        redisTemplate.opsForHash().delete(key, fieldKey);

        AnswerVote vote = new AnswerVote();
        vote.setUserId(userId);
        vote.setAnswerId(form.getAnswerId());
        vote.setStatus(form.getStatus());
        vote.setCreateTime(new Date());

        // add to redis first, and use quartz schedule to store it into MySQL
        redisTemplate.opsForHash().put(key, fieldKey, JSONUtil.toJsonStr(vote));

    }

    @Override
    public void voteQuestionToDatabase() {
        Set<Object> keys = redisTemplate.opsForHash().keys("question_vote");
        List<Object> question_vote = redisTemplate.opsForHash().multiGet("question_vote", keys);


        for(Object obj : question_vote) {
            QuestionVote vote = JSONUtil.parse(obj).toBean(QuestionVote.class);
            // Get the vote from database, delete it if exists to prevent reinsertion
            QuestionVote voteInDb = voteDao.getQuestionVoteByUserIdAndQuestionId(vote.getUserId(), vote.getQuestionId());
            if (voteInDb != null) {
                // delete the data in the database if it exists
                voteDao.deleteQuestionVoteByUserIdAndQuestionId(vote.getUserId(), vote.getQuestionId());
            }
            voteDao.insertVoteQuestion(vote);
        }

        // Delete all the keys in the hash set
        redisTemplate.delete("question_vote");
    }

    @Override
    public void voteAnswerToDatabase() {

    }

    @Autowired
    public VoteServiceImpl(QuestionDao questionDao, VoteDao voteDao, AnswerDao answerDao, RedisTemplate<String, String> redisTemplate) {
        this.questionDao = questionDao;
        this.voteDao = voteDao;
        this.answerDao = answerDao;
        this.redisTemplate = redisTemplate;
    }
}
