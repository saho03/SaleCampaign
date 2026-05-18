package com.SALE.SaleCampaign.Service;

import com.SALE.SaleCampaign.DTO.PageDTO;
import com.SALE.SaleCampaign.Model.PriceHistory;
import com.SALE.SaleCampaign.Model.Product;
import com.SALE.SaleCampaign.Repository.PriceHistoryRepository;
import com.SALE.SaleCampaign.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    public void saveAll(List<Product> products) {
        for (Product product : products) {
            product.setCurrentPrice(product.getMrp());
            product.setDiscount(0);
        }

        List<Product> saved = productRepository.saveAll(products);

        for (Product product : saved) {
            saveProductCreatedHistory(product);
        }
    }

    public PageDTO getProduct(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Product> productPage = productRepository.findAll(pageable);

        PageDTO dto = new PageDTO();
        dto.setProducts(productPage.getContent());
        dto.setPageNo(page);
        dto.setSize(productPage.getContent().size());
        dto.setTotalPage(productPage.getTotalPages());

        return dto;
    }

    private void saveProductCreatedHistory(Product product) {
        Date today = Date.valueOf(LocalDate.now());

        // Duplicate guard
        PriceHistory existing = priceHistoryRepository.getHistoryByDateProductAndType(today, product.getId(), "PRODUCT_CREATED");
        if (existing != null) return;

        PriceHistory history = new PriceHistory();
        history.setProduct(product);
        history.setCampaign(null);
        history.setCampaignTitle("N/A");
        history.setOldPrice(0);
        history.setNewPrice(product.getCurrentPrice());
        history.setDiscount(0);
        history.setDate(today);
        history.setChangeType("PRODUCT_CREATED");

        priceHistoryRepository.save(history);
        System.out.println("[History] Product created: '" + product.getTitle()
                + "' | Price: " + product.getCurrentPrice());
    }
}