package com.ulearn.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.ulearn.controller.response.JsonResponse;
import com.ulearn.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2023/3/12 3:56
 */

@RestController
@RequestMapping("/message")
@Tag(name = "MessageController", description = "信息控制器")
public class MessageController {

    private MessageService messageService;

    @GetMapping("/get-inbox-message")
    @Operation(description = "获取信息箱")
    @SaCheckLogin
    public JsonResponse addAnswer(@RequestParam Long userId) {
        HashMap map = messageService.getInboxMessageByUserId(userId);
        return JsonResponse.ok(map);
    }

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
}
