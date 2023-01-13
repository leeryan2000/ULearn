package com.ulearn.service.config;

import com.ulearn.service.impl.HelloJob;
import com.ulearn.service.impl.VoteJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import javax.validation.executable.ValidateOnExecution;

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
        // Execute every 10 seconds
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(voteJobDetail())
                .withIdentity("voteJobTrigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    // @Bean
    // public JobDetail helloJobDetail() {
    //
    //     return JobBuilder.newJob(HelloJob.class)
    //             .withIdentity("DateTimeJob")
    //             .usingJobData("msg", "Hello Quartz")
    //             .storeDurably()//即使没有Trigger关联时，也不需要删除该JobDetail.
    //             .build();
    // }
    //
    // @Bean
    // public Trigger printTimeJobTrigger() {
    //     // 每秒执行一次
    //     CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");
    //     return TriggerBuilder.newTrigger()
    //             .forJob(helloJobDetail())
    //             .withIdentity("quartzTaskService")
    //             .withSchedule(cronScheduleBuilder)
    //             .build();
    // }
}