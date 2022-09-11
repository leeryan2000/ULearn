package com.ulearn.service;

import com.ulearn.dao.domain.Answer;
import com.ulearn.dao.domain.FollowAnswer;
import com.ulearn.dao.form.AnswerForm;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:33
 */
public interface AnswerService {

    void addAnswer(Long userId, AnswerForm form);
}
