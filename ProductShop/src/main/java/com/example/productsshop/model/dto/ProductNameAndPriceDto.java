package com.example.productsshop.model.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Email;
import java.math.BigDecimal;

public class ProductNameAndPriceDto {
    @Email
    private String name;
    @Expose
    private BigDecimal price;

    public ProductNameAndPriceDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
