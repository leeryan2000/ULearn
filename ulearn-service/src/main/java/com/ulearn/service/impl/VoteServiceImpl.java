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
import com.ulearn.service.util.PostRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void voteQuestion(Long userId, VoteQuestionForm form) {
        Question question = questionDao.getQuestionById(form.getQuestionId());
        if (question == null) {
            throw new CommonRuntimeException(CommonOperationError.QUESTION_DOESNT_EXIST);
        }

        String key = "question_vote";
        String fieldKey =  userId + "-" + form.getQuestionId();
        String voteJsonStr = (String) redisTemplate.opsForHash().get(key, fieldKey);
        QuestionVote vote = JSONUtil.parse(voteJsonStr).toBean(QuestionVote.class);

        boolean flag = false;
        if (vote == null) {
            vote = voteDao.getQuestionVoteByUserIdAndQuestionId(userId, form.getQuestionId());
            flag = true;
        }

        if (vote == null || vote.getStatus() != form.getStatus()) {
            // delete the item from MySQL if it exists to prevent from re insertion
            if (flag) voteDao.deleteQuestionVoteByUserIdAndQuestionId(userId, form.getQuestionId());
            redisTemplate.opsForHash().delete(key, fieldKey);

            vote = new QuestionVote();
            vote.setUserId(userId);
            vote.setQuestionId(form.getQuestionId());
            vote.setStatus(form.getStatus());
            vote.setCreateTime(new Date());

            // add to redis first, and use quartz schedule to store it into MySQL
            redisTemplate.opsForHash().put(key, fieldKey, JSONUtil.toJsonStr(vote));
        }
        else {
            // 重新做相同决定的投票 (delete vote)
            if (flag) voteDao.deleteQuestionVoteByUserIdAndQuestionId(userId, form.getQuestionId());
            redisTemplate.opsForHash().delete(key, fieldKey);
        }
    }

    @Override
    public void voteAnswer(Long userId, VoteAnswerForm form) {
        Answer answer = answerDao.getAnswerById(form.getAnswerId());
        if (answer == null) {
            throw new CommonRuntimeException(CommonOperationError.QUESTION_DOESNT_EXIST);
        }

        String key = "question_vote";
        String fieldKey =  userId + "-" + form.getAnswerId();
        String voteJsonStr = (String) redisTemplate.opsForHash().get(key, fieldKey);
        AnswerVote vote = JSONUtil.parse(voteJsonStr).toBean(AnswerVote.class);

        boolean flag = false;
        if (vote == null) {
            vote = voteDao.getAnswerVoteByUserIdAndAnswerId(userId, form.getAnswerId());
            flag = true;
        }

        if (vote == null || vote.getStatus() != form.getStatus()) {
            // delete the item from MySQL if it exists to prevent from re insertion
            if (flag) voteDao.deleteAnswerVoteByUserIdAndAnswerId(userId, form.getAnswerId());
            redisTemplate.opsForHash().delete(key, fieldKey);

            vote = new AnswerVote();
            vote.setUserId(userId);
            vote.setAnswerId(form.getAnswerId());
            vote.setStatus(form.getStatus());
            vote.setCreateTime(new Date());

            // add to redis first, and use quartz schedule to store it into MySQL
            redisTemplate.opsForHash().put(key, fieldKey, JSONUtil.toJsonStr(vote));
        }
        else {
            // 重新做相同决定的投票 (delete vote)
            if (flag) voteDao.deleteQuestionVoteByUserIdAndQuestionId(userId, form.getAnswerId());
            redisTemplate.opsForHash().delete(key, fieldKey);
        }
    }

    @Autowired
    public VoteServiceImpl(QuestionDao questionDao, VoteDao voteDao, AnswerDao answerDao, RedisTemplate<String, String> redisTemplate) {
        this.questionDao = questionDao;
        this.voteDao = voteDao;
        this.answerDao = answerDao;
        this.redisTemplate = redisTemplate;
    }
}
