package com.ulearn.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.ulearn.controller.response.JsonResponse;
import com.ulearn.dao.form.AnswerForm;
import com.ulearn.dao.form.QuestionForm;
import com.ulearn.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 15:03
 */

@RestController
@Tag(name = "PostController", description = "发表控制器")
public class PostController {

    private final PostService postService;

    @PostMapping("add-question")
    @Operation(description = "添加问题")
    @SaCheckLogin
    public JsonResponse addQuestion(@Valid @RequestBody QuestionForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        postService.addQuestion(form, userId);
        return JsonResponse.ok();
    }

    @PostMapping("add-answer")
    @Operation(description = "添加回答")
    @SaCheckLogin
    public JsonResponse addAnswer(@Valid @RequestBody AnswerForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        postService.addAnswer(form, userId);
        return JsonResponse.ok();
    }

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
}
