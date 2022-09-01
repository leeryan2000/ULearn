package com.ulearn.dao.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/8/31 15:49
 */

@Data
@Schema(description = "用户登入格式")
public class UserSignUpForm {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "用户密码不能为空")
    private String password;
}
