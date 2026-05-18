package com.SALE.SaleCampaign.Controller;

import com.SALE.SaleCampaign.DTO.PageDTO;
import com.SALE.SaleCampaign.Model.Product;
import com.SALE.SaleCampaign.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("saleCampaign")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("addproduct")
    public ResponseEntity<String> saveall(@RequestBody List<Product> products) {
        productService.saveAll(products);
        return ResponseEntity.ok("Product Add Successfully");
    }

    @GetMapping("getproduct")
    public ResponseEntity<PageDTO> getproduct(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "5") int pagesize
    ) {
        return ResponseEntity.ok(productService.getProduct(pageNo,pagesize));
    }
}
