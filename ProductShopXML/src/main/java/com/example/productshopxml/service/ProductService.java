package com.example.productshopxml.service;

import com.example.productshopxml.model.dto.productsInRangeDtos.ProductInRangeRootDto;
import com.example.productshopxml.model.dto.seedDtos.ProductSeedDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void seedProducts(List<ProductSeedDto> productSeedDtos);

    ProductInRangeRootDto getProductsInRange(BigDecimal Low, BigDecimal high);
}
