package com.example.productsshop.model.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class ProductsWithCountDto {
    @Expose
    private int count;
    @Expose
    private List<ProductNameAndPriceDto> products;

    public ProductsWithCountDto() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = this.products.size();
    }

    public List<ProductNameAndPriceDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductNameAndPriceDto> products) {
        this.products = products;
    }
}
