package com.ulearn.dao.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 16:19
 */

@Data
@Schema(description = "回答")
public class Answer {

    private Long id;

    private Long userId;

    private Long questionId;

    private String questionContent;

    private String content;

    private Date createTime;

    private Boolean accepted;

    private Date acceptedTime;
}
