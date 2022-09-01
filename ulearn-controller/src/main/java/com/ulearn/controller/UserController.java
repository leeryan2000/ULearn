package com.ulearn.controller;

import cn.hutool.json.JSONUtil;
import com.ulearn.controller.response.JsonResponse;
import com.ulearn.dao.domain.User;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.error.CommonSystemError;
import com.ulearn.dao.form.UserSignUpForm;
import com.ulearn.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * @Author: Ryan
 * @Description: U
 * @Date: 2022/8/30 22:24
 */

@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "用户Web接口")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add-user")
    @Operation(description = "添加用户")
    public JsonResponse addUser(@Valid @RequestBody UserSignUpForm form) {
        userService.addUser(form);
        return JsonResponse.ok();
    }
}
