package com.ulearn.dao.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/5 16:29
 */

@Data
@Schema(description = "投票回答模板")
public class VoteAnswerForm {

    private Long answerId;

    private Boolean status;
}
