package com.ulearn.service.util;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.HashMap;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/26 13:37
 */

@Component
public class PostRedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    public void addMessageByUserId(Long userId, HashMap message) {
        String key = "inbox_message_" + userId;
        redisTemplate.opsForList().leftPush(key, JSONUtil.toJsonStr(message));
    }

    @Autowired
    public PostRedisUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
