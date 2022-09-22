package com.ulearn.dao.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/22 14:49
 */

@Data
public class QuestionComment {

    private Long id;

    private Long userId;

    private Long questionId;

    private String content;

    private Long replyId;

    private Date createTime;
}
