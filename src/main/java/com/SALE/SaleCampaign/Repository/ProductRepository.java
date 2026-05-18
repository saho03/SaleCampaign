package com.SALE.SaleCampaign.Repository;

import com.SALE.SaleCampaign.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value = """
            SELECT IFNULL(SUM(cd.discount), 0)
            FROM campaign_discount cd
            JOIN campaign c ON cd.campaign_id = c.campaign_id
            WHERE cd.product_id = ?1
              AND c.is_started = true
              AND c.is_ended = false
              AND CURRENT_DATE() BETWEEN c.start_date AND c.end_date
            """, nativeQuery = true)
    double getTotalDiscount(int productId);

    @Query(value = """
            SELECT IFNULL(SUM(cd.discount), 0)
            FROM campaign_discount cd
            JOIN campaign c ON cd.campaign_id = c.campaign_id
            WHERE cd.product_id = ?1
              AND c.campaign_id != ?2
              AND c.is_started = true
              AND c.is_ended = false
              AND CURRENT_DATE() BETWEEN c.start_date AND c.end_date
            """, nativeQuery = true)
    double getDiscountExcludingCurrent(int productId, int campaignId);
}
