package com.ulearn.service.config;

import com.ulearn.service.impl.VoteJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/10/6 22:41
 */

@Configuration
public class QuartzConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    @QuartzDataSource
    public DataSource quartzDataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    @Bean
    public JobDetail voteJobDetail() {
        return JobBuilder.newJob(VoteJob.class)
                .withIdentity("VoteJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger voteJobTrigger() {
        // 设置定时时间 (execute every 2 hours)
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0/2 * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(voteJobDetail())
                .withIdentity("voteJobTrigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}