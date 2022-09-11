package com.ulearn.service;

import com.ulearn.dao.form.BookmarkForm;
import com.ulearn.dao.form.BookmarkGroupForm;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/11 16:11
 */
public interface BookmarkService {

    void addBookmark(Long userId, BookmarkForm form);

    void addBookmarkGroup(Long userId, BookmarkGroupForm form);
}
