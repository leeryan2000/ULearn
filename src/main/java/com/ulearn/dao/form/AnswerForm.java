package com.ulearn.dao.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 17:03
 */

@Data
@Schema(description = "回答模板")
public class AnswerForm {

    @NotNull(message = "问题ID不能为空")
    private Long questionId;

    @NotBlank(message = "回答不能为空")
    private String content;
}
