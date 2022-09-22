package com.ulearn.dao.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/6 21:44
 */

@Data
public class AnswerFollow {

    private Long userId;

    private Long answerId;

    private Date createTime;
}
