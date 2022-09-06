package com.ulearn.dao.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/6 21:44
 */

@Data
public class FollowQuestion {

    private Long userId;

    private Long questionId;

    private Date createTime;
}
