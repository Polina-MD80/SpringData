package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<Shampoo> findByBrandAndSize(String brand, Size size);

    List<Shampoo> getAllBySizeOrderById(Size sizeValue);

    List<Shampoo> findShampooBySizeOrLabel_IdOrderByPrice(Size size, Long labelId);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countAllByPriceLessThan(BigDecimal price);

    List<String> findAllShampooByIngredientNames(List<String> names);

    List<String> findShampooByNumberOfIngredients(int number);
}
