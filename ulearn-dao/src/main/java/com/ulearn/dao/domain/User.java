package com.ulearn.dao.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/8/30 22:29
 */

@Data
@Schema(description = "用户注册")
public class User {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Date createTime;

    private String key;
}
