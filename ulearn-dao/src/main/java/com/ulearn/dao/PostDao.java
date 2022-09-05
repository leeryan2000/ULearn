package com.ulearn.dao;

import com.ulearn.dao.domain.Answer;
import com.ulearn.dao.domain.Question;
import com.ulearn.dao.domain.VoteAnswer;
import com.ulearn.dao.domain.VoteQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 15:24
 */

@Mapper
public interface PostDao {

    Question getQuestionById(@Param("questionId") Long questionId);

    Answer getAnswerById(@Param("answerId") Long answerId);

    Integer addQuestion(Question question);

    Integer addAnswer(Answer answer);

    Integer voteQuestion(VoteQuestion vote);

    Integer voteAnswer(VoteAnswer vote);

    VoteQuestion getVoteQuestionByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    VoteAnswer getVoteAnswerByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

    Integer deleteVoteQuestionByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    Integer deleteVoteAnswerByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

}
