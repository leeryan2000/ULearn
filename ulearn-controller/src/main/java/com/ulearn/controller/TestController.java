package com.ulearn.controller;

import cn.hutool.json.JSONUtil;
import com.ulearn.controller.response.JsonResponse;
import com.ulearn.dao.FollowDao;
import com.ulearn.dao.constant.PostMQConstant;
import com.ulearn.service.util.RocketMQUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/19 13:00
 */

@RestController
@RequestMapping("/test")
public class TestController {

    private final ApplicationContext applicationContext;

    private final RedisTemplate<String, String> redisTemplate;

    private final FollowDao followDao;

    @GetMapping("/sql")
    public JsonResponse testSql() {
        return JsonResponse.ok();
    }

    @GetMapping("/produce")
    public JsonResponse produce() throws Exception {

        // DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("questionMessageProducer");
        //
        // String tmp;
        // for(int i = 0; i < 10; i++) {
        //     tmp = i + "";
        //     Message msg = new Message(PostMQConstant.FOLLOW_POST_MESSAGE_TOPIC, tmp.getBytes());
        //     RocketMQUtil.syncSendMsg(producer, msg);
        // }

        return JsonResponse.ok();
    }

    @GetMapping("/redis")
    public JsonResponse redisTest() {
        List<HashMap> hashMaps = new ArrayList<>();

        HashMap map = new HashMap();
        map.put("one", "test");
        map.put("two", "test");
        map.put("three", "test");

        HashMap map1 = new HashMap();
        map1.put("one", "test");
        map1.put("two", "test");
        map1.put("three", "test");

        hashMaps.add(map);
        hashMaps.add(map1);

        String jsonStr = JSONUtil.toJsonStr(hashMaps);
        redisTemplate.opsForValue().set("hashmap", jsonStr);

        String hashmap = redisTemplate.opsForValue().get("hashmap");
        List<HashMap> hashMaps1 = JSONUtil.parseArray(hashmap).toList(HashMap.class);
        System.out.println(hashMaps1);

        return JsonResponse.ok();
    }

    @GetMapping("/redisList")
    public JsonResponse redisTestList() {
        List<HashMap> list = new ArrayList<>();

        HashMap map = new HashMap();
        map.put("one", "test");
        map.put("two", "test");
        map.put("three", "test");

        HashMap map1 = new HashMap();
        map1.put("one", "test");
        map1.put("two", "test");
        map1.put("three", "test");

        list.add(map);
        list.add(map1);

        // redisTemplate.opsForValue().set("list", JSONUtil.toJsonStr(list));

        HashMap map2 = new HashMap();
        map2.put("one", "test");
        map2.put("two", "test");
        map2.put("three", "test");

        // redisTemplate.opsForList().leftPush("list", JSONUtil.toJsonStr(map2));

        redisTemplate.opsForHash().put("hashMap", "map2", JSONUtil.toJsonStr(map2));

        redisTemplate.opsForHash().put("hashMap", "map1", JSONUtil.toJsonStr(map1));



        return JsonResponse.ok();
    }

    @GetMapping("/sendMessage1")
    public JsonResponse sendMessage1() throws Exception {

        DefaultMQProducer test_producer_1 = (DefaultMQProducer) applicationContext.getBean("TEST_PRODUCER");
        Message message = new Message("TEST_TOPIC_1", JSONUtil.toJsonStr("HI").getBytes());
        RocketMQUtil.syncSendMsg(test_producer_1, message);

        return JsonResponse.ok();
    }

    @GetMapping("/sendMessage2")
    public JsonResponse sendMessage2() throws Exception {

        DefaultMQProducer test_producer_2 = (DefaultMQProducer) applicationContext.getBean("TEST_PRODUCER");
        Message message = new Message("TEST_TOPIC_2", JSONUtil.toJsonStr("HI").getBytes());
        RocketMQUtil.syncSendMsg(test_producer_2, message);

        return JsonResponse.ok();
    }

    @Autowired
    public TestController(ApplicationContext applicationContext, RedisTemplate<String, String> redisTemplate, FollowDao followDao) {
        this.applicationContext = applicationContext;
        this.redisTemplate = redisTemplate;
        this.followDao = followDao;
    }
}


