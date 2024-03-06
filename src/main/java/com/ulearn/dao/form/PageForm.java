package com.ulearn.dao.form;

import cn.hutool.db.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PageForm {

    @NotNull(message="頁碼不能為空")
    private Long pageNum;

    @NotNull(message="頁面大小不能為空")
    private Long pageSize;

    public PageForm(Long pageNum, Long pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
