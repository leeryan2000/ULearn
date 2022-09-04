package com.ulearn.dao.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 16:26
 */

@Data
@Schema(description = "标签")
public class Tag {

    private Long id;

    private String name;
}
