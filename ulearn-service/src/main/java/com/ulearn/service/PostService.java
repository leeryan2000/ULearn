package com.ulearn.service;

import com.ulearn.dao.form.AnswerForm;
import com.ulearn.dao.form.QuestionForm;
import com.ulearn.dao.form.VoteAnswerForm;
import com.ulearn.dao.form.VoteQuestionForm;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 15:04
 */

public interface PostService {

    void addQuestion(Long userId, QuestionForm form);

    void addAnswer(Long userId, AnswerForm form);

    void voteQuestion(Long userId, VoteQuestionForm form);

    void voteAnswer(Long userId, VoteAnswerForm form);
}
