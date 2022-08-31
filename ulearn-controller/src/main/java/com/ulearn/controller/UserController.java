package com.ulearn.controller;

import com.ulearn.dao.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ryan
 * @Description: U
 * @Date: 2022/8/30 22:24
 */

@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "用户Web接口")
public class UserController {

    @PostMapping("/adduser")
    public void addUser(User user) {

    }
}
