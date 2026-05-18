package com.SALE.SaleCampaign.DTO;

import com.SALE.SaleCampaign.Model.Product;

import java.util.List;

public class PageDTO {
    private List<Product> products;
    private int totalPage;
    private int size;
    private int pageNo;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
