package com.ulearn.service;

import com.ulearn.dao.form.AnswerForm;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:33
 */
public interface AnswerService {

    void addAnswer(Long userId, AnswerForm form) throws Exception;
}
