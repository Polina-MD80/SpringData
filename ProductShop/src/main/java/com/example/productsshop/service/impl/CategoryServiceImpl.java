package com.example.productsshop.service.impl;

import com.example.productsshop.constants.GlobalConstants;
import com.example.productsshop.model.dto.CategoryByProductsDto;
import com.example.productsshop.model.dto.CategorySeedDto;
import com.example.productsshop.model.entity.Category;
import com.example.productsshop.model.entity.Product;
import com.example.productsshop.repository.CategoryRepository;
import com.example.productsshop.repository.UserRepository;
import com.example.productsshop.service.CategoryService;
import com.example.productsshop.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.productsshop.constants.GlobalConstants.FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private static final String CATEGORIES_FILE_NAME = "categories.json";

    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() >0){
            return;
        }
      String data = Files.readString(Path.of(FILE_PATH+CATEGORIES_FILE_NAME));

        CategorySeedDto[] categorySeedDtos = gson.fromJson(data, CategorySeedDto[].class);
        Arrays.stream(categorySeedDtos)
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

    @Override
    public List<CategoryByProductsDto> getCategoriesInfo() {
        List<Category> categories = categoryRepository.CategoriesByProductsCount();

       return categories.stream()
                .map(category -> {
                    CategoryByProductsDto categoryByProductsDto = modelMapper.map(category, CategoryByProductsDto.class);
                    setStatistics(category,categoryByProductsDto);
                    return categoryByProductsDto;
                }).collect(Collectors.toList());

    }

    private void setStatistics(Category category, CategoryByProductsDto categoryByProductsDto) {
        int count = category.getProducts().size();
        BigDecimal averagePrice = BigDecimal.ZERO;
        BigDecimal totalRevenue = category.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (count>0){
        averagePrice = totalRevenue.divide(BigDecimal.valueOf(count), RoundingMode.FLOOR);}
        categoryByProductsDto.setProductsCount(count);
        categoryByProductsDto.setTotalRevenue(totalRevenue);
        categoryByProductsDto.setAveragePrice(averagePrice);


    }
}
