package com.ulearn.service;

import com.ulearn.dao.domain.Question;
import com.ulearn.dao.form.*;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 15:04
 */

public interface QuestionService {

    void addQuestion(Long userId, QuestionForm form);

    List<HashMap> getQuestionByPage(PageForm pageForm);
}
