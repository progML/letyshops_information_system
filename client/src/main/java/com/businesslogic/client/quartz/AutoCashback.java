package com.businesslogic.client.quartz;

import com.businesslogic.client.service.CashBackService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;


@RequiredArgsConstructor
public class AutoCashback implements Job {

    private final CashBackService cashBackService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        cashBackService.sendCashBack();
    }
}