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
public class CommentAnswerForm {

    @NotNull(message = "回答ID不能为空")
    private Long answerId;

    @NotBlank(message = "评论不能为空")
    private String content;

    @Schema(description = "回复的用户ID, 默认为空")
    private Long replyId;
}
