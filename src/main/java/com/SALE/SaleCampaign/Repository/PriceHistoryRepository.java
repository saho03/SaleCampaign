package com.SALE.SaleCampaign.Repository;

import com.SALE.SaleCampaign.Model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer> {

    // Used for duplicate check — finds existing history by date, product and change type
    @Query(value = "SELECT * FROM price_history " +
            "WHERE date = ?1 " +
            "AND product_id = ?2 " +
            "AND change_type = ?3 " +
            "LIMIT 1",
            nativeQuery = true)
    PriceHistory getHistoryByDateProductAndType(Date date, int productId, String changeType);

    // Get full history for a specific product
    @Query(value = "SELECT * FROM price_history " +
            "WHERE product_id = ?1 " +
            "ORDER BY date DESC",
            nativeQuery = true)
    List<PriceHistory> getHistoryByProduct(int productId);

    // Get full history for a specific campaign
    @Query(value = "SELECT * FROM price_history " +
            "WHERE campaign_id = ?1 " +
            "ORDER BY date DESC",
            nativeQuery = true)
    List<PriceHistory> getHistoryByCampaign(int campaignId);
}