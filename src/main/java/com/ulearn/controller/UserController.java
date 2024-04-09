package com.ulearn.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.ulearn.controller.response.JsonResponse;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.UserLoginForm;
import com.ulearn.dao.form.UserSignUpForm;
import com.ulearn.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Ryan
 * @Description: User controller layer
 * @Date: 2022/8/30 22:24
 */

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "User web interface")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add-user")
    @Operation(description = "Add user")
    public JsonResponse addUser(@Valid @RequestBody UserSignUpForm form) {
        userService.addUser(form);
        return JsonResponse.ok();
    }

    @PostMapping("/login")
    @Operation(description = "User login")
    public JsonResponse addUser(@Valid @RequestBody UserLoginForm form) {
        if (StpUtil.isLogin()) {
            throw new CommonRuntimeException(CommonOperationError.USER_ALREADY_LOGIN);
        }

        Long userId = userService.login(form);
        StpUtil.login(userId);
        log.info("User logged in ID: " + userId);
        return JsonResponse.ok();
    }

    @GetMapping("/logout")
    @Operation(description = "User logout")
    @SaCheckLogin
    public JsonResponse logout() {
        StpUtil.logout();
        return JsonResponse.ok();
    }
}
