package com.ulearn.service;

import com.ulearn.dao.form.CommentAnswerForm;
import com.ulearn.dao.form.CommentQuestionForm;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/22 22:21
 */

public interface CommentService {

    void addQuestionComment(Long userId, CommentQuestionForm form);

    void addAnswerComment(Long userId, CommentAnswerForm form);
}
