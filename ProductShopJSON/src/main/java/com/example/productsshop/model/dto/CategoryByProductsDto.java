package com.example.productsshop.model.dto;

import com.example.productsshop.model.entity.Product;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.Set;

public class CategoryByProductsDto {
    @Expose
    private String name;
    @Expose
    private Integer productsCount;
    @Expose
    private BigDecimal averagePrice;
    @Expose
    private BigDecimal totalRevenue;

    private Set<Product> products;

    public CategoryByProductsDto() {
    }

    public String getName() {
        return name;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
