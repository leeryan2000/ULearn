package com.ulearn.dao.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/6 21:20
 */

@Data
public class FollowQuestionForm {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "问题ID不能为空")
    private Long questionId;
}
