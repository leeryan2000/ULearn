package com.ulearn.dao.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/6 22:07
 */

@Data
public class Bookmark {

    private Long userId;

    private Long questionId;

    private Long groupId;

    private Date createTime;
}
