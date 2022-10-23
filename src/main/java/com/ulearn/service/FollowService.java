package com.ulearn.service;

import com.ulearn.dao.form.FollowAnswerForm;
import com.ulearn.dao.form.FollowQuestionForm;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:05
 */
public interface FollowService {

    void followQuestion(Long userId, FollowQuestionForm form);

    void followAnswer(Long userId, FollowAnswerForm form);
}
