package com.ulearn.dao;

import com.ulearn.dao.domain.AnswerFollow;
import com.ulearn.dao.domain.QuestionFollow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:12
 */
@Mapper
public interface FollowDao {

    Integer followQuestion(QuestionFollow questionFollow);

    QuestionFollow getQuestionFollowByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    QuestionFollow deleteQuestionFollowByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    Integer followAnswer(AnswerFollow answerFollow);

    AnswerFollow getAnswerFollowByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

    AnswerFollow deleteAnswerFollowByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

    List<Long> getQuestionFollowerByQuestionId(@Param("questionId") Long questionId);

    List<Long> getAnswerFollowerByAnswerId(@Param("answerId") Long answerId);

    HashMap getFollowedQuestionAnswerByAnswerId(@Param("answerId") Long answerId);
}
