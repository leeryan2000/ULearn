package com.ulearn.controller;

import com.ulearn.dao.domain.User;
import com.ulearn.dao.form.UserSignUpForm;
import com.ulearn.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public void addUser(@Valid @RequestBody UserSignUpForm form) {
        userService.addUser(form);
    }
}
