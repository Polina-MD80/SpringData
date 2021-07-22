package com.example.cardealer.model.dto;

import com.example.cardealer.model.entity.Car;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.List;

public class CustomerTotalSalesDto {
    @Expose
    private String fullName;
    @Expose
    private Integer boughtCars;
    @Expose
    private BigDecimal Price;
    private List<SalePriceDto> sales;
    private List<Car> cars;

    public List<SalePriceDto> getSales() {
        return sales;
    }

    public void setSales(List<SalePriceDto> sales) {
        this.sales = sales;
    }

    public CustomerTotalSalesDto() {
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(Integer boughtCars) {
        this.boughtCars = boughtCars;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }
}
