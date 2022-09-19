package com.ulearn.controller;

import com.ulearn.controller.response.JsonResponse;
import com.ulearn.dao.constant.PostMQConstant;
import com.ulearn.service.util.RocketMQUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/19 13:00
 */

@RestController
@RequestMapping("/test")
public class TestController {

    private final ApplicationContext applicationContext;

    @GetMapping("/produce")
    public JsonResponse produce() throws Exception {

        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("questionMessageProducer");

        String tmp;
        for(int i = 0; i < 10; i++) {
            tmp = i + "";
            Message msg = new Message(PostMQConstant.POST_QUESTION_TOPIC, tmp.getBytes());
            RocketMQUtil.syncSendMsg(producer, msg);
        }

        return JsonResponse.ok();
    }

    @Autowired
    public TestController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
