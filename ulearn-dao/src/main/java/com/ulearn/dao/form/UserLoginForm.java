package com.ulearn.dao.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/3 15:09
 */

@Data
@Schema(description = "用户注册格式")
public class UserLoginForm {

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户密码")
    private String password;
}
