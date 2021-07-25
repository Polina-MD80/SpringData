package com.example.productshopxml;

import com.example.productshopxml.model.dto.*;
import com.example.productshopxml.repository.UserRepository;
import com.example.productshopxml.service.CategoryService;
import com.example.productshopxml.service.ProductService;
import com.example.productshopxml.service.UserService;
import com.example.productshopxml.util.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

@Component
public class MainProductShop implements CommandLineRunner {
    private final XmlParser xmlParser;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;
    private static final String FILES_PATH_RESOURCE = "src/main/resources/files/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private static final String USERS_FILE_NAME = "users.xml";
    private static final String PRODUCTS_FILE_NAME = "products.xml";
    private static final String OUT_DIR_PATH = "src/main/resources/files/out/";

    public MainProductShop(XmlParser xmlParser, CategoryService categoryService,
                           UserRepository userRepository, UserService userService,
                           ProductService productService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        while (true){
            System.out.println("Enter task number from 1 - 3:");
            int taskNumber = Integer.parseInt(bufferedReader.readLine());

            switch (taskNumber){
                case 1 -> productsInRange();
                case 2 -> usersSoldProducts();
            }
        }





    }

    private void usersSoldProducts() throws JAXBException {
       UserSoldProductsRootDto userSoldProductsRootDto = userService.getUsersSoldProducts();
               xmlParser.toFile(OUT_DIR_PATH + "users-sold-products.xml" , userSoldProductsRootDto);
    }

    private void productsInRange() throws JAXBException {
        ProductInRangeRootDto productInRangeRootDto = productService.getProductsInRange(BigDecimal.valueOf(500),BigDecimal.valueOf(1000));
        xmlParser.toFile(OUT_DIR_PATH + "products-in-range.xml", productInRangeRootDto);
    }

    private void seedData() throws JAXBException, FileNotFoundException {
//seed Categories
        CategoryRootSeedDto categoryRootSeedDto = xmlParser.fromFile(FILES_PATH_RESOURCE + CATEGORIES_FILE_NAME,
                CategoryRootSeedDto.class);
        categoryService.seedCategories(categoryRootSeedDto.getCategorySeedDtos());
// Seed users

        UserSeedRootDto userSeedRootDto = xmlParser.fromFile(FILES_PATH_RESOURCE + USERS_FILE_NAME,
                UserSeedRootDto.class);
        userService.seedUsers(userSeedRootDto.getUserSeedDtos());
 // Seed products
        ProductRootSeedDto productRootSeedDto = xmlParser.fromFile(FILES_PATH_RESOURCE + PRODUCTS_FILE_NAME,
                ProductRootSeedDto.class);
        productService.seedProducts(productRootSeedDto.getProductSeedDtos());




    }
}
