package com.example.cardealer.model.dto;

import com.example.cardealer.model.entity.Car;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class SaleDiscountCustomerDto {
    @Expose
    private CarWithPriceAndDistDto car;
    @Expose
    private String customerName;
    @Expose
    private BigDecimal Discount;
    @Expose
    private BigDecimal price;
    @Expose
    private BigDecimal priceWithDiscount;

    public SaleDiscountCustomerDto() {
    }

    public CarWithPriceAndDistDto getCar() {
        return car;
    }

    public void setCar(CarWithPriceAndDistDto car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getDiscount() {
        return Discount;
    }

    public void setDiscount(BigDecimal discount) {
        Discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
