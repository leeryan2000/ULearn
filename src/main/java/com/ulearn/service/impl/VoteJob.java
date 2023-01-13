package com.ulearn.service.impl;

import com.ulearn.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2023/1/8 19:55
 */

@Slf4j
public class VoteJob extends QuartzJobBean{

    private final VoteService voteService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("Votes for question and answer updated to database");
        log.info("Update time: " + new Date());
        voteService.voteQuestionToDatabase();
        voteService.voteAnswerToDatabase();
    }

    @Autowired
    public VoteJob(VoteService voteService) {
        this.voteService = voteService;
    }
}
