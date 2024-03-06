package com.ulearn.dao;

import com.ulearn.dao.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 15:24
 */

@Mapper
public interface QuestionDao {

    Question getQuestionById(@Param("questionId") Long questionId);

    Integer addQuestion(Question question);

    Integer addTag(Tag tag);

    Integer addQuestionTag(QuestionTag questionTag);

    List<HashMap> getQuestionByPage(@Param("offset") Long offset, @Param("pageSize") Long pageSize);
}
