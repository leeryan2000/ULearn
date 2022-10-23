package com.ulearn.dao;

import com.ulearn.dao.domain.AnswerVote;
import com.ulearn.dao.domain.QuestionVote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:12
 */

@Mapper
public interface VoteDao {

    Integer voteQuestion(QuestionVote vote);

    QuestionVote getQuestionVoteByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    Integer deleteQuestionVoteByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    Integer voteAnswer(AnswerVote vote);

    AnswerVote getAnswerVoteByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

    Integer deleteAnswerVoteByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

}
