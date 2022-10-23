package com.ulearn.dao.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/3 21:41
 */

@Data
@Schema(description = "问题")
public class Question {

    private Long id;

    private Long userId;

    private String title;

    private String content;

    private Date createTime;

    private Integer view;
}
