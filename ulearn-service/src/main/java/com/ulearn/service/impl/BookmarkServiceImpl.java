package com.ulearn.service.impl;

import com.ulearn.dao.BookmarkDao;
import com.ulearn.dao.domain.Bookmark;
import com.ulearn.dao.domain.BookmarkGroup;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.BookmarkForm;
import com.ulearn.dao.form.BookmarkGroupForm;
import com.ulearn.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:11
 */

@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkDao bookmarkDao;

    @Override
    public void addBookmark(Long userId, BookmarkForm form) {
        List<Long> groupIds = form.getGroupIds();
        Bookmark bookmark;

        for (Long groupId : groupIds) {
            bookmark = new Bookmark();
            bookmark.setUserId(userId);
            bookmark.setQuestionId(form.getQuestionId());
            bookmark.setGroupId(groupId);
            bookmark.setCreateTime(new Date());
            Integer rows = bookmarkDao.addBookmark(bookmark);
            if (rows != 1) {
                throw new CommonRuntimeException(CommonOperationError.CREATE_BOOKMARK_FAILED);
            }
        }
    }

    @Override
    public void addBookmarkGroup(Long userId, BookmarkGroupForm form) {
        BookmarkGroup bookmarkGroup = new BookmarkGroup();

        bookmarkGroup.setUserId(userId);
        bookmarkGroup.setName(form.getName());

        Integer rows = bookmarkDao.addBookmarkGroup(bookmarkGroup);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.CREATE_BOOKMARK_GROUP_FAILED);
        }
    }

    @Autowired
    public BookmarkServiceImpl(BookmarkDao bookmarkDao) {
        this.bookmarkDao = bookmarkDao;
    }
}
