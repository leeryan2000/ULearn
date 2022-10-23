package com.ulearn.dao;

import com.ulearn.dao.domain.Bookmark;
import com.ulearn.dao.domain.BookmarkGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:12
 */

@Mapper
public interface BookmarkDao {

    Integer addBookmark(Bookmark bookmark);

    Integer addBookmarkGroup(BookmarkGroup bookmarkGroup);

    List<Bookmark> getBookmarkByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

    List<BookmarkGroup> getBookmarkGroupByUserId(@Param("userId") Long userId);

    void deleteBookmarkByUserIdAndQuestionIdAndGroupId(@Param("userId") Long userId, @Param("questionId") Long questionId, @Param("groupId") Long groupId);

}
