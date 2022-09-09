package com.ulearn.dao;

import com.ulearn.dao.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 15:24
 */

@Mapper
public interface PostDao {

    // Question
    Question getQuestionById(@Param("questionId") Long questionId);

    Integer addQuestion(Question question);

    Integer voteQuestion(VoteQuestion vote);

    VoteQuestion getVoteQuestionByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    Integer deleteVoteQuestionByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    Integer followQuestion(FollowQuestion followQuestion);

    FollowQuestion getFollowQuestionByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    FollowQuestion deleteFollowQuestionByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    // Tag
    Integer addTag(Tag tag);

    Integer addQuestionTag(QuestionTag questionTag);

    // Bookmark
    Integer addBookmark(Bookmark bookmark);

    Integer addBookmarkGroup(BookmarkGroup bookmarkGroup);

    List<Bookmark> getBookmarkByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    List<BookmarkGroup> getBookmarkGroupByUserId(@Param("userId") Long userId);

    void deleteBookmarkByUserIdAndQuestionIdAndGroupId(@Param("userId") Long userId, @Param("questionId") Long questionId, @Param("groupId") Long groupId);

    // Answer
    Answer getAnswerById(@Param("answerId") Long answerId);

    Integer addAnswer(Answer answer);

    Integer voteAnswer(VoteAnswer vote);

    VoteAnswer getVoteAnswerByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

    Integer deleteVoteAnswerByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

    Integer followAnswer(FollowAnswer followAnswer);

    FollowAnswer getFollowAnswerByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);

    FollowAnswer deleteFollowAnswerByUserIdAndAnswerId(@Param("userId") Long userId, @Param("answerId") Long answerId);
}
