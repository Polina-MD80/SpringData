package com.example.productshopxml.service.impl;

import com.example.productshopxml.model.dto.CategorySeedDto;
import com.example.productshopxml.model.entity.Category;
import com.example.productshopxml.repository.CategoryRepository;
import com.example.productshopxml.service.CategoryService;
import com.example.productshopxml.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories(List<CategorySeedDto> categorySeedDtos) {
        if(categoryRepository.count()>0){
            return;
        }
        categorySeedDtos.stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Category getRandomCategory() {
        long count = this.categoryRepository.count();
        Long catNum = ThreadLocalRandom.current().nextLong(1, count + 1);
        Category category = this.categoryRepository.findById(catNum).orElse(null);
        return category;
    }
}
