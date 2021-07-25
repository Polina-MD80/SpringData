package com.example.productshopxml.service;

import com.example.productshopxml.model.dto.seedDtos.CategorySeedDto;
import com.example.productshopxml.model.entity.Category;

import java.util.List;

public interface CategoryService {

    void seedCategories(List<CategorySeedDto> categorySeedDtos);

    Category getRandomCategory();
}
