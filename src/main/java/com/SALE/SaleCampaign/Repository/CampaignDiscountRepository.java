package com.SALE.SaleCampaign.Repository;

import com.SALE.SaleCampaign.Model.CampaignDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CampaignDiscountRepository extends JpaRepository<CampaignDiscount, Integer> {

    @Query(value = "SELECT COALESCE(SUM(cd.discount), 0) " +
            "FROM campaign_discount cd " +
            "JOIN campaign c ON cd.campaign_id = c.campaign_id " +
            "WHERE cd.product_id = ?1 " +
            "AND c.start_date <= CURRENT_DATE() " +
            "AND c.end_date >= CURRENT_DATE() " +
            "AND c.is_started = true " +
            "AND c.is_ended = false",
            nativeQuery = true)
    double getTotalActiveDiscountForProduct(int productId);

    // FIX 5: Removed 'static' keyword — interface methods in JPA repositories
    //        cannot be static; Spring Data creates the proxy implementation at runtime
    @Query(value = "SELECT COALESCE(SUM(cd.discount), 0) " +
            "FROM campaign_discount cd " +
            "JOIN campaign c ON cd.campaign_id = c.campaign_id " +
            "WHERE cd.product_id = ?1 " +
            "AND c.campaign_id != ?2 " +
            "AND c.start_date <= CURRENT_DATE() " +
            "AND c.end_date >= CURRENT_DATE() " +
            "AND c.is_started = true " +
            "AND c.is_ended = false",
            nativeQuery = true)
    double getOtherActiveDiscountExcludingCampaign(int productId, int excludeCampaignId);
}