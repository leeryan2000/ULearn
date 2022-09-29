package com.ulearn.service.util;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/26 13:37
 */

@Component
public class MessageRedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    public synchronized void addMessageByUserId(Long userId, HashMap message) {
        String key = "inbox_message_" + userId;
        String messageListStr = redisTemplate.opsForValue().get(key);
        List<HashMap> messageList;
        if (messageListStr == null) {
            messageList = new ArrayList<>();
        }
        else {
            messageList = JSONUtil.parseArray(messageListStr).toList(HashMap.class);
        }
        messageList.add(message);
        messageListStr = JSONUtil.toJsonStr(messageList);
        redisTemplate.opsForValue().set(key, messageListStr);
    }

    @Autowired
    public MessageRedisUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
