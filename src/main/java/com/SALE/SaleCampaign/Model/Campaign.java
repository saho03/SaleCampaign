package com.SALE.SaleCampaign.Model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campaignId;
    private String title;
    private Date stratDate;
    private Date endDate;

    @Column(name = "is_strat",nullable = false,columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isStart = false;

    @Column(name = "is_end",nullable = false,columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isEnd = false;

    @OneToMany(mappedBy = "campaign",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CampaignDiscount> campaignDiscounts;

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStratDate() {
        return stratDate;
    }

    public void setStratDate(Date stratDate) {
        this.stratDate = stratDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public List<CampaignDiscount> getCampaignDiscounts() {
        return campaignDiscounts;
    }

    public void setCampaignDiscounts(List<CampaignDiscount> campaignDiscounts) {
        this.campaignDiscounts = campaignDiscounts;
    }
}
