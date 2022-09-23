package com.ulearn.dao.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/22 22:25
 */

@Data
@Schema(description = "用户评论问题格式")
public class CommentQuestionForm {

    @NotNull(message = "问题ID不能为空")
    private Long questionId;

    @NotBlank(message = "回答不能为空")
    private String content;
}
