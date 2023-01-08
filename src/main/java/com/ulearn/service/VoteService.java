package com.ulearn.service;

import com.ulearn.dao.form.VoteAnswerForm;
import com.ulearn.dao.form.VoteQuestionForm;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:05
 */
public interface VoteService {

    void voteQuestion(Long userId, VoteQuestionForm form);

    void voteAnswer(Long userId, VoteAnswerForm form);

    void voteQuestionToDatabase();

    void voteAnswerToDatabase();
}
