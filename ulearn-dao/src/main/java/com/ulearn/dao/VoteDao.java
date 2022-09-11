package com.ulearn.dao;

import com.ulearn.dao.domain.VoteAnswer;
import com.ulearn.dao.domain.VoteQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:12
 */

@Mapper
public interface VoteDao {

    Integer voteAnswer(VoteAnswer vote);

    VoteAnswer getVoteAnswerByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

    Integer deleteVoteAnswerByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

    Integer voteQuestion(VoteQuestion vote);

    VoteQuestion getVoteQuestionByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    Integer deleteVoteQuestionByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

}
