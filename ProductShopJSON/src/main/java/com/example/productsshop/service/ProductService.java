package com.example.productsshop.service;

import com.example.productsshop.model.dto.ProductPriceSellerNameDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts() throws IOException;

    List<ProductPriceSellerNameDto> findAllProductsInPriceRange(BigDecimal lower, BigDecimal upper);
}
