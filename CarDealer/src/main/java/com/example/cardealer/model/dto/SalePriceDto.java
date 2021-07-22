package com.example.cardealer.model.dto;

import com.example.cardealer.model.entity.Car;
import com.example.cardealer.model.entity.Part;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.List;

public class SalePriceDto {
    private Car car;
    @Expose
    private BigDecimal price;
    private BigDecimal priceWithoutDiscount;
    private List<Part> parts;

    public SalePriceDto() {
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithoutDiscount() {
        return priceWithoutDiscount;
    }

    public void setPriceWithoutDiscount(BigDecimal priceWithoutDiscount) {
        this.priceWithoutDiscount = priceWithoutDiscount;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
