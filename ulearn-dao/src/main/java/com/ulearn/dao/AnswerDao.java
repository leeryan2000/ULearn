package com.ulearn.dao;

import com.ulearn.dao.domain.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:48
 */

@Mapper
public interface AnswerDao {

    Answer getAnswerById(@Param("answerId") Long answerId);

    Integer addAnswer(Answer answer);
}
