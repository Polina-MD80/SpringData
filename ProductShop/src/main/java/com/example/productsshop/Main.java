package com.example.productsshop;

import com.example.productsshop.constants.GlobalConstants;
import com.example.productsshop.model.dto.*;
import com.example.productsshop.service.CategoryService;
import com.example.productsshop.service.ProductService;
import com.example.productsshop.service.UserService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.example.productsshop.constants.GlobalConstants.*;

@Component
public class Main implements CommandLineRunner {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BufferedReader reader;
    private final Gson gson;



    public Main(UserService userService, ProductService productService,
                CategoryService categoryService, Gson gson) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        while (true) {
            System.out.println("Enter task number from 1 to 4:");
            int taskNumber = Integer.parseInt(reader.readLine());

            switch (taskNumber) {
                case 1 -> productsInRange();
                case 2 -> successfullySoldProducts();
                case 3 -> CategoriesByProductsCount();
                case 4 -> usersAndProducts();
                default -> System.out.println("Invalid task number!");
            }
        }
    }

    private void usersAndProducts() throws IOException {
        UsersCountDto usersCountDtos = this.userService.getUsersAndProductsWithCount();
        String content = gson.toJson(usersCountDtos);
        writeToFile(OUTPUT_FILES_PATH + USERS_AND_PRODUCTS, content);
    }

    private void CategoriesByProductsCount() throws IOException {
        List<CategoryByProductsDto> categoryByProductsDtos = categoryService.getCategoriesInfo();
        String content = gson.toJson(categoryByProductsDtos);
        writeToFile(OUTPUT_FILES_PATH + CATEGORIES_BY_PRODUCTS, content);
    }

    private void successfullySoldProducts() throws IOException {
        List<UserSellerDto> userSellerDtos = userService.findAllSellersAndTheirSoldProducts();
        String content = gson.toJson(userSellerDtos);
        writeToFile(OUTPUT_FILES_PATH + USERS_WITH_SOLD_PRODUCTS, content);
    }

    private void productsInRange() throws IOException {
        List<ProductPriceSellerNameDto> productPriceSellerNameDtos = productService
                .findAllProductsInPriceRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
        String content = gson.toJson(productPriceSellerNameDtos);
        writeToFile(OUTPUT_FILES_PATH + PRODUCTS_IN_RANGE, content);
    }

    private void writeToFile(String path, String content) throws IOException {
        Files.write(Path.of(path), Collections.singleton(content));

    }

    private void seedData() throws IOException {
        this.userService.seedUsers();
        this.categoryService.seedCategories();
        this.productService.seedProducts();
    }
}
