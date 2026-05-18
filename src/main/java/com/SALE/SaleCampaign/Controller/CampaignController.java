package com.SALE.SaleCampaign.Controller;

import com.SALE.SaleCampaign.Model.Campaign;
import com.SALE.SaleCampaign.Service.CampaignApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    @Autowired
    CampaignApplyService campaignApplyService;

    @PostMapping("/add")
    public ResponseEntity<String > addCampaign(@RequestBody Campaign campaign) {
        return campaignApplyService.addCampaign(campaign);
    }
}
