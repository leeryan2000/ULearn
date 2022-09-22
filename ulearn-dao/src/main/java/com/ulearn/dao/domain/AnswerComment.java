package com.ulearn.dao.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/22 14:48
 */

@Data
public class AnswerComment {

    private Long id;
    
    private Long userId;

    private Long answerId;

    private String content;

    private Long replyId;

    private Date createTime;
}
