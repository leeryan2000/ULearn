package com.ulearn.dao.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/5 16:29
 */

@Data
@Schema(description = "投票问题模板")
public class VoteQuestionForm {

    @NotNull(message = "问题ID不能为空")
    private Long questionId;

    @NotNull(message = "不能为空")
    private Boolean status;
}
