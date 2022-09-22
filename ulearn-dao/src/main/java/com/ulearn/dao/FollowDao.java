package com.ulearn.dao;

import com.ulearn.dao.domain.FollowAnswer;
import com.ulearn.dao.domain.FollowQuestion;
import com.ulearn.dao.domain.User;
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

    Integer followQuestion(FollowQuestion followQuestion);

    FollowQuestion getFollowQuestionByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    FollowQuestion deleteFollowQuestionByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    Integer followAnswer(FollowAnswer followAnswer);

    FollowAnswer getFollowAnswerByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

    FollowAnswer deleteFollowAnswerByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

    List<Long> getQuestionFollowerByQuestionId(@Param("questionId") Long questionId);

    HashMap getFollowedQuestionAnswerByAnswerId(@Param("answerId") Long answerId);
}
