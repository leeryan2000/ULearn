package com.ulearn.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.ulearn.controller.response.JsonResponse;
import com.ulearn.dao.form.*;
import com.ulearn.service.*;
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

    private final QuestionService questionService;

    private final AnswerService answerService;

    private final VoteService voteService;

    private final FollowService followService;

    private final BookmarkService bookmarkService;

    @PostMapping("/add-question")
    @Operation(description = "添加问题")
    @SaCheckLogin
    public JsonResponse addQuestion(@Valid @RequestBody QuestionForm form) throws Exception {
        Long userId = StpUtil.getLoginIdAsLong();
        questionService.addQuestion(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/add-answer")
    @Operation(description = "添加回答")
    @SaCheckLogin
    public JsonResponse addAnswer(@Valid @RequestBody AnswerForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        answerService.addAnswer(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/vote-question")
    @Operation(description = "问题投票")
    @SaCheckLogin
    public JsonResponse voteQuestion(@Valid @RequestBody VoteQuestionForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        voteService.voteQuestion(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/vote-answer")
    @Operation(description = "回答投票")
    @SaCheckLogin
    public JsonResponse voteAnswer(@Valid @RequestBody VoteAnswerForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        voteService.voteAnswer(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/add-bookmark")
    @Operation(description = "添加书签")
    @SaCheckLogin
    public JsonResponse addBookmark(@Valid @RequestBody BookmarkForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        bookmarkService.addBookmark(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/follow-question")
    @Operation(description = "追踪问题")
    @SaCheckLogin
    public JsonResponse followQuestion(@Valid @RequestBody FollowQuestionForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        followService.followQuestion(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/follow-answer")
    @Operation(description = "追踪问题")
    @SaCheckLogin
    public JsonResponse followAnswer(@Valid @RequestBody FollowAnswerForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        followService.followAnswer(userId, form);
        return JsonResponse.ok();
    }

    @Autowired
    public PostController(QuestionService questionService, AnswerService answerService, VoteService voteService, FollowService followService, BookmarkService bookmarkService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.voteService = voteService;
        this.followService = followService;
        this.bookmarkService = bookmarkService;
    }
}
