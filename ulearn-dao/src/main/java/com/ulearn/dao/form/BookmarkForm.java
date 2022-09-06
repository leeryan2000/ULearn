package com.ulearn.dao.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/6 16:49
 */

@Data
public class BookmarkForm {

    @NotNull(message = "问题ID不能为空")
    private Long questionId;
}
