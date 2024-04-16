package com.ulearn.controller;

import com.ulearn.controller.response.JsonResponse;
import com.ulearn.dao.form.PageForm;
import com.ulearn.service.QuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/get")
@Tag(name = "GetController", description = "Controller that helps getting questions, answers and comments posted by the user")
public class GetController {

    private final QuestionService questionService;

    @GetMapping("/get-question")
    public JsonResponse getQuestion(@RequestParam Long pageNum, @RequestParam Long pageSize) {
        PageForm pageForm = new PageForm(pageNum, pageSize);
        List<HashMap> listQuestion = questionService.getQuestionByPage(pageForm);
        return JsonResponse.ok(listQuestion);
    }

    @Autowired
    public GetController(QuestionService questionService) {
        this.questionService = questionService;
    }
}
