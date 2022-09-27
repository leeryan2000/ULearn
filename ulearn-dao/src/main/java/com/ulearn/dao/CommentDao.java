package com.ulearn.dao;

import com.ulearn.dao.domain.Answer;
import com.ulearn.dao.domain.AnswerComment;
import com.ulearn.dao.domain.QuestionComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/22 21:59
 */

@Mapper
public interface CommentDao {

    Integer addQuestionComment(QuestionComment questionComment);

    Answer getQuestionCommentById(@Param("commentId") Long commentId);

    Integer addAnswerComment(AnswerComment answerComment);

    Answer getAnswerCommentById(@Param("commentId") Long commentId);

    HashMap getQuestionCommentByCommentId(@Param("commentId") Long commentId);

    HashMap getAnswerCommentByCommentId(@Param("commentId") Long commentId);
}
