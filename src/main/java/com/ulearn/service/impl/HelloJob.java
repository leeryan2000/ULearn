package com.ulearn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/10/6 22:40
 */

@Slf4j
public class HelloJob extends QuartzJobBean {

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // Show the detail of the job
        context.getJobDetail().getJobDataMap().forEach(
                (k, v) -> log.info("param, key:{}, value:{}", k, v)
        );

        redisTemplate.opsForValue().set("Quartz", "Test");


        // your logics
        log.info("Hello Job执行时间: " + new Date());
    }
}