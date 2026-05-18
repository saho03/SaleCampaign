package com.SALE.SaleCampaign.Repository;

import com.SALE.SaleCampaign.Model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

    // FIX 1: Return type was List<CampaignApplyService> — must be List<Campaign>
    // FIX 2: SQL was broken: "andDate = <=" is invalid syntax, column names were wrong
    //        (stratDate → start_date, isStart → is_started, iSEND → is_ended)
    @Query(value = "SELECT * FROM campaign " +
            "WHERE start_date <= CURRENT_DATE() " +
            "AND end_date >= CURRENT_DATE() " +
            "AND is_started = false " +
            "AND is_ended = false", nativeQuery = true)
        List<Campaign> findCampaignToStart();

    // FIX 3: Return type same fix as above
    // FIX 4: Column name corrected (iSEND → is_ended)
    @Query(value = "SELECT * FROM campaign " +
            "WHERE end_date <= CURRENT_DATE() " +
            "AND is_ended = false", nativeQuery = true)
    List<Campaign> findCampaignToEnd();
}