package com.example.springbatch.scheduler;


import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public  class FileJobRunner extends JobRunner{


    private Scheduler  scheduler;


    protected void  doRun(ApplicationArguments args) {

        String[] sourceArgs = args.getSourceArgs();
        JobDetail jobDetail = buildJobDetail(Scheduler.class, "fileJob", "batch", new HashMap());
        Trigger trigger = buildJobTrigger("0/50 * * * * ?");
        jobDetail.getJobDataMap().put("requestDate", sourceArgs[0]);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}


