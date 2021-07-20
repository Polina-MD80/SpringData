package com.example.productsshop.model.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Products {
    @Expose
    private int count;
    @Expose
    private List<ProductNameAndPriceDto> products;

    public Products() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProductNameAndPriceDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductNameAndPriceDto> products) {
        this.products = products;
    }
}
