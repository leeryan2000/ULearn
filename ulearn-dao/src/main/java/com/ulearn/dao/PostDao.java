package com.ulearn.dao;

import com.ulearn.dao.domain.Answer;
import com.ulearn.dao.domain.Question;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 15:24
 */

@Mapper
public interface PostDao {

    Integer addQuestion(Question question);

    Integer addAnswer(Answer answer);
}
