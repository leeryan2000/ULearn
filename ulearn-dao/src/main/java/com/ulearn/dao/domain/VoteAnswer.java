package com.ulearn.dao.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/5 15:27
 */

@Data
public class VoteAnswer {

    private Long userId;

    private Long answerId;

    private Boolean status;

    private Date createTime;
}
