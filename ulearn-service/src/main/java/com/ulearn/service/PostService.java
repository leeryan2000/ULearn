package com.ulearn.service;

import com.ulearn.dao.form.AnswerForm;
import com.ulearn.dao.form.QuestionForm;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 15:04
 */

public interface PostService {

    void addQuestion(QuestionForm form, Long userId);

    void addAnswer(AnswerForm form, Long userId);
}
