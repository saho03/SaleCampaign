package com.SALE.SaleCampaign.Scheduler;

import com.SALE.SaleCampaign.Service.CampaignApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerService {

    @Autowired
    private CampaignApplyService campaignApplyService;

    /**
     * Runs every day at midnight 00:00
     * Starts all campaigns whose start_date = today
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void startCampaigns() {
        System.out.println("[Scheduler] Running startCampaigns...");
        campaignApplyService.starCampaign();
    }

    /**
     * Runs every day at midnight 00:01
     * Ends all campaigns whose end_date = today
     */
    @Scheduled(cron = "0 1 0 * * *")
    public void endCampaigns() {
        System.out.println("[Scheduler] Running endCampaigns...");
        campaignApplyService.endCampaign();
    }
}