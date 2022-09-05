package com.ulearn.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.ulearn.controller.response.JsonResponse;
import com.ulearn.dao.form.AnswerForm;
import com.ulearn.dao.form.QuestionForm;
import com.ulearn.dao.form.VoteAnswerForm;
import com.ulearn.dao.form.VoteQuestionForm;
import com.ulearn.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/4 15:03
 */

@RestController
@RequestMapping("/post")
@Tag(name = "PostController", description = "发表控制器")
public class PostController {

    private final PostService postService;

    @PostMapping("/add-question")
    @Operation(description = "添加问题")
    @SaCheckLogin
    public JsonResponse addQuestion(@Valid @RequestBody QuestionForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        postService.addQuestion(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/add-answer")
    @Operation(description = "添加回答")
    @SaCheckLogin
    public JsonResponse addAnswer(@Valid @RequestBody AnswerForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        postService.addAnswer(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/vote-question")
    @Operation(description = "问题投票")
    @SaCheckLogin
    public JsonResponse voteQuestion(@Valid @RequestBody VoteQuestionForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        postService.voteQuestion(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/vote-answer")
    @Operation(description = "回答投票")
    @SaCheckLogin
    public JsonResponse voteAnswer(@Valid @RequestBody VoteAnswerForm form) {
        return JsonResponse.ok();
    }


    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
}
