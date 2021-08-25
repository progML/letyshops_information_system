package com.businesslogic.client.config;


import com.businesslogic.client.quartz.AutoCashback;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;

//http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-04.html

@Configuration
public class ApplicationConfig {



    @Bean
    JobDetail veryHardJob() {
        return JobBuilder.newJob(AutoCashback.class)
                .withIdentity("autoCashback")
                .storeDurably()
                .build();
    }

    @Bean
    Trigger jobTrigger(){
        return TriggerBuilder.newTrigger().forJob(veryHardJob())
                .withIdentity("autoCashbackTrigger")
                .startNow()
                .withSchedule(dailyAtHourAndMinute(12, 9))
                .build();
    }
}
