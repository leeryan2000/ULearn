package com.ulearn.service.impl;

import com.ulearn.dao.QuestionDao;
import com.ulearn.dao.domain.*;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.*;
import com.ulearn.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 17:07
 */

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    // 保證事務
    @Transactional
    @Override
    public void addQuestion(Long userId, QuestionForm form) {
        Question question = new Question();

        question.setUserId(userId);
        question.setTitle(form.getTitle());
        question.setContent(form.getContent());
        question.setCreateTime(new Date());

        Integer rows = questionDao.addQuestion(question);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.POST_FAILED);
        }

        // Add question tags
        List<Long> tags = form.getTags();
        QuestionTag questionTag;
        for (Long tagId : tags) {
            questionTag = new QuestionTag();

            questionTag.setQuestionId(question.getId());
            questionTag.setTagId(tagId);

            rows = questionDao.addQuestionTag(questionTag);
            if (rows != 1) {
                throw new CommonRuntimeException(CommonOperationError.QUESTION_TAG_ADD_FAILED);
            }
        }
    }

    @Override
    public List<HashMap> getQuestionByPage(PageForm pageForm) {
        Long pageNum = pageForm.getPageNum();
        Long pageSize = pageForm.getPageSize();

        Long offset = (pageNum - 1) * pageSize;

        List<HashMap> listQuestion = questionDao.getQuestionByPage(offset, pageSize);
        if (listQuestion == null) {
            throw new CommonRuntimeException(CommonOperationError.QUESTION_QUERY_BY_PAGE_FAILED);
        }

        return listQuestion;
    }


    @Autowired
    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }
}
