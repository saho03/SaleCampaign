package com.SALE.SaleCampaign.Service;

import com.SALE.SaleCampaign.Model.Campaign;
import com.SALE.SaleCampaign.Model.CampaignDiscount;
import com.SALE.SaleCampaign.Model.PriceHistory;
import com.SALE.SaleCampaign.Model.Product;
import com.SALE.SaleCampaign.Repository.CampaignDiscountRepository;
import com.SALE.SaleCampaign.Repository.CampaignRepository;
import com.SALE.SaleCampaign.Repository.PriceHistoryRepository;
import com.SALE.SaleCampaign.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class CampaignApplyService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    PriceHistoryRepository priceHistoryRepository;

    @Autowired
    CampaignDiscountRepository campaignDiscountRepository;

    @Transactional
    public void starCampaign() {
        List<Campaign> campaignsToStart = campaignRepository.findCampaignToStart();

        if (campaignsToStart.isEmpty()) {
         System.out.println("No Campaign to Start Today");
         return;
     }
     processCampaigns(campaignsToStart,true);
    }

    @Transactional
    public void endCampaign() {
       List<Campaign> campaignsToEnd = campaignRepository.findCampaignToEnd();
        if (campaignsToEnd.isEmpty()) {
            System.out.println("No Campaign to End Today");
            return;
        }
        processCampaigns(campaignsToEnd,false
        );
    }

    @Transactional
    public ResponseEntity<String> addCampaign(Campaign campaign) {
        try {
            if (campaign.getCampaignDiscounts() == null || campaign.getCampaignDiscounts().isEmpty()) {
                return new ResponseEntity<>("Campaign must have at least one product discount", HttpStatus.BAD_REQUEST);
            }

            if (campaign.getStratDate() == null || campaign.getEndDate() == null) {
                return new ResponseEntity<>("Start date and end date are required", HttpStatus.BAD_REQUEST);
            }

            if (campaign.getStratDate().after(campaign.getEndDate())) {
                return new ResponseEntity<>("Start date must be before end date",HttpStatus.BAD_REQUEST);
            }

            for (CampaignDiscount cd : campaign.getCampaignDiscounts()) {
                Product product = cd.getProduct();

                if (product == null || product.getId() == 0) {
                    return new ResponseEntity<>("Invalid product Id",HttpStatus.BAD_REQUEST);
                }

                Product managed = productRepository.findById(product.getId()).orElseThrow(() ->  new RuntimeException("Product not Found" + product.getId()));

                cd.setProduct(managed);
                cd.setCampaign(campaign);
            }
            Campaign saved = campaignRepository.save(campaign);
            System.out.println("[Campaign] Saved ID: " + saved.getCampaignId());

            LocalDate today = LocalDate.now();
            LocalDate startDate = saved.getStratDate().toLocalDate();

            if (!startDate.isAfter(today)) {
                processCampaigns(List.of(saved),true);
                return new ResponseEntity<>("Campaign created & discount applied immediately! ID: " + saved.getCampaignId(),HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Campaign Create! will apply on"+ startDate + ".ID:" + saved.getCampaignId(),HttpStatus.CREATED);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error:" + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void processCampaigns(List<Campaign> campaigns,boolean isStart) {
        int batchsize = 50;
        int count = 0;

        List<Product> proBatch = new ArrayList<>();
        List<PriceHistory> hisBatch = new ArrayList<>();

        for (Campaign campaign : campaigns) {
            for (CampaignDiscount cd : campaign.getCampaignDiscounts()) {
                try {
                    Product product = cd.getProduct();
                    double oldPrice = product.getCurrentPrice();
                    double discount = cd.getDiscount();

                    if (discount <= 0 || discount >= 100) {
                        continue;
                    }

                    double newPrice = isStart
                            ? oldPrice - (oldPrice * discount / 100)
                            : oldPrice / (1 - discount / 100);

                    product.setCurrentPrice(newPrice);
                    proBatch.add(product);
                    hisBatch.add(priceHistory(product,campaign,oldPrice,newPrice,discount,isStart));

                    count++;

                    if (count % batchsize == 0) {
                        productRepository.saveAll(proBatch);
                        priceHistoryRepository.saveAll(hisBatch);
                        proBatch.clear();
                        hisBatch.clear();
                    }
                }catch (Exception e) {
                    throw new RuntimeException("Failed Product:" + e.getMessage());
                }
            }

            if (isStart) {
                campaign.setStart(true);
            }else {
                campaign.setEnd(true);
            }

            if (!proBatch.isEmpty()) {
                productRepository.saveAll(proBatch);
                priceHistoryRepository.saveAll(hisBatch);
            }
            campaignRepository.saveAll(campaigns);
        }
    }

    public PriceHistory priceHistory(Product product,Campaign campaign,double oldPrice,
                                     double newPrice,double discount,boolean isStart) {
        PriceHistory history = new PriceHistory();
        history.setProduct(product);
        history.setCampaign(campaign);
        history.setCampaignTitle(campaign.getTitle());
        history.setOldPrice(oldPrice);
        history.setNewPrice(newPrice);
        history.setDiscount(discount);
        history.setDate(Date.valueOf(LocalDate.now()));
        history.setChangeType(isStart ? "CAMPAIGN_START" : "CAMPAIGN_END");
        return history;
    }


}


