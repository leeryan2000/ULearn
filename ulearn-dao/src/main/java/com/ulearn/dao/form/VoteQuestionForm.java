package com.ulearn.dao.form;

import lombok.Data;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/5 16:29
 */

@Data
public class VoteQuestionForm {

    private Long questionId;

    private Boolean status;
}
