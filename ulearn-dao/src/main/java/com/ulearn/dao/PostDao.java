package com.ulearn.dao;

import com.ulearn.dao.domain.Answer;
import com.ulearn.dao.domain.Question;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 15:24
 */
public interface PostDao {

    Integer addQuestion(Question question);

    Integer addAnswer(Answer answer);
}
