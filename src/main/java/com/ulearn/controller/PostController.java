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
@Tag(name = "PostController", description = "Controller for posting questions, answers and comments")
public class PostController {

    private final QuestionService questionService;

    private final AnswerService answerService;

    private final CommentService commentService;

    private final VoteService voteService;

    private final FollowService followService;

    private final BookmarkService bookmarkService;

    @PostMapping("/add-question")
    @Operation(description = "Add question")
    @SaCheckLogin
    public JsonResponse addQuestion(@Valid @RequestBody QuestionForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        questionService.addQuestion(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/add-answer")
    @Operation(description = "Add answer")
    @SaCheckLogin
    public JsonResponse addAnswer(@Valid @RequestBody AnswerForm form) throws Exception {
        Long userId = StpUtil.getLoginIdAsLong();
        answerService.addAnswer(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/vote-question")
    @Operation(description = "Question vote")
    @SaCheckLogin
    public JsonResponse voteQuestion(@Valid @RequestBody VoteQuestionForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        voteService.voteQuestion(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/vote-answer")
    @Operation(description = "Answer vote")
    @SaCheckLogin
    public JsonResponse voteAnswer(@Valid @RequestBody VoteAnswerForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        voteService.voteAnswer(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/add-bookmark")
    @Operation(description = "Add bookmark")
    @SaCheckLogin
    public JsonResponse addBookmark(@Valid @RequestBody BookmarkForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        bookmarkService.addBookmark(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/follow-question")
    @Operation(description = "Follow question")
    @SaCheckLogin
    public JsonResponse followQuestion(@Valid @RequestBody FollowQuestionForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        followService.followQuestion(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/follow-answer")
    @Operation(description = "Follow answer")
    @SaCheckLogin
    public JsonResponse followAnswer(@Valid @RequestBody FollowAnswerForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        followService.followAnswer(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/comment-question")
    @Operation(description = "Add comment for question")
    @SaCheckLogin
    public JsonResponse commentQuestion(@Valid @RequestBody CommentQuestionForm form) throws Exception {
        Long userId = StpUtil.getLoginIdAsLong();
        commentService.addQuestionComment(userId, form);
        return JsonResponse.ok();
    }

    @PostMapping("/comment-answer")
    @Operation(description = "Add comment for answer")
    @SaCheckLogin
    public JsonResponse answerQuestion(@Valid @RequestBody CommentAnswerForm form) throws Exception {
        Long userId = StpUtil.getLoginIdAsLong();
        commentService.addAnswerComment(userId, form);
        return JsonResponse.ok();
    }

    @Autowired
    public PostController(QuestionService questionService, AnswerService answerService, CommentService commentService, VoteService voteService, FollowService followService, BookmarkService bookmarkService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.commentService = commentService;
        this.voteService = voteService;
        this.followService = followService;
        this.bookmarkService = bookmarkService;
    }
}
