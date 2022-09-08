package com.ulearn.service;

import com.ulearn.dao.form.*;

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

    void addBookmark(Long userId, BookmarkForm form);

    void addBookmarkGroup(Long userId, BookmarkGroupForm form);

    void followQuestion(Long userId, FollowQuestionForm form);

    void followAnswer(Long userId, FollowAnswerForm form);
}
