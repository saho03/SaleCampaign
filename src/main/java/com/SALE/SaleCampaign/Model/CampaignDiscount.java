package com.SALE.SaleCampaign.Model;

import jakarta.persistence.*;

@Entity
public class CampaignDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campaignDiscountId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "campaignId")
    private Campaign campaign;

    private double discount;

    public int getCampaignDiscountId() {
        return campaignDiscountId;
    }

    public void setCampaignDiscountId(int campaignDiscountId) {
        this.campaignDiscountId = campaignDiscountId;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}