package com.ulearn.dao.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/7 13:34
 */

@Data
public class BookmarkGroupForm {

    @NotNull(message = "书签组名不能为空")
    private String name;
}
