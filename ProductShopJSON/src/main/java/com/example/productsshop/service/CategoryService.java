package com.example.productsshop.service;

import com.example.productsshop.model.dto.CategoryByProductsDto;
import com.example.productsshop.model.entity.Category;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    void seedCategories() throws IOException;

    List<CategoryByProductsDto> getCategoriesInfo();

    Category getRandomCategory();
}
