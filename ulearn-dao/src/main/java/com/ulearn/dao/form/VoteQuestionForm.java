package com.ulearn.dao.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/5 16:29
 */

@Data
@Schema(description = "投票问题模板")
public class VoteQuestionForm {

    @NotBlank
    private Long questionId;

    @NotBlank
    private Boolean status;
}
