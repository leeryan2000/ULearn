package com.ulearn.dao.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/6 21:20
 */

@Data
public class FollowAnswerForm {

    @NotNull(message = "回答ID不能为空")
    private Long answerId;
}
