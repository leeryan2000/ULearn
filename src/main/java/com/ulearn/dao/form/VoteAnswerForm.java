package com.ulearn.dao.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/5 16:29
 */

@Data
@Schema(description = "投票回答模板")
public class VoteAnswerForm {

    @NotNull(message = "问题ID不能为空")
    private Long answerId;

    @NotNull
    private Boolean status;
}
