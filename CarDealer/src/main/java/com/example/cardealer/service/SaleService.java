package com.example.cardealer.service;

import com.example.cardealer.model.dto.SaleDiscountCustomerDto;
import com.example.cardealer.model.entity.Sale;

import java.util.List;

public interface SaleService {
    void seedData();



    List<Sale> getAllSales();

    List<SaleDiscountCustomerDto> getAllSalesWithDiscount();
}
