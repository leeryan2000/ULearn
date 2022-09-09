package com.ulearn.dao.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 17:03
 */

@Data
@Schema(description = "问题模板")
public class QuestionForm {

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    private List<Long> tags;
}
