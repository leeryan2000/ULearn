package com.ulearn.service;

import com.ulearn.dao.form.*;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 15:04
 */

public interface QuestionService {

    void addQuestion(Long userId, QuestionForm form) throws Exception;
}
