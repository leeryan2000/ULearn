package com.ulearn.service.impl;

import com.ulearn.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2023/3/11 2:05
 */

@Service
public class MessageServiceImpl implements MessageService {

    private RedisTemplate<String, String> redisTemplate;

    @Override
    public HashMap getInboxMessageByUserId(Long userId) {

        List<String> list = redisTemplate.opsForList().range("inbox_message_" + userId, 0, -1);
        HashMap map = new HashMap();
        map.put("inbox_message", list);
        return map;
    }

    @Autowired
    public MessageServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
