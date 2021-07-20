package com.example.productsshop.service.impl;

import com.example.productsshop.model.dto.ProductPriceSellerNameDto;
import com.example.productsshop.model.dto.ProductSeedDto;
import com.example.productsshop.model.entity.Product;
import com.example.productsshop.repository.ProductRepository;
import com.example.productsshop.service.CategoryService;
import com.example.productsshop.service.ProductService;
import com.example.productsshop.service.UserService;
import com.example.productsshop.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.productsshop.constants.GlobalConstants.FILE_PATH;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private static final String PRODUCTS_FILE_NAME = "products.json";
    private final UserService userService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, UserService userService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void seedProducts() throws IOException {

        if (this.productRepository.count() > 0) {
            return;
        }

        String data = Files.readString(Path.of(FILE_PATH+PRODUCTS_FILE_NAME));

        ProductSeedDto[] productSeedDtos = gson.fromJson(data, ProductSeedDto[].class);

        Arrays.stream(productSeedDtos)
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                  Product product=  modelMapper.map(productSeedDto, Product.class);
                  if (product.getName().charAt(0) != 'E'){
                      product.setBuyer(this.userService.getRandomUser());
                  }
                  product.setSeller(userService.getRandomUser());
                  setCategories(product);
                  return product;
                })
                .forEach(productRepository::save);

    }

    private void setCategories(Product product) {
        int numberOfCategories = ThreadLocalRandom.current().nextInt(1,4);
        for (int i = 0; i < numberOfCategories; i++) {
            product.getCategories().add(categoryService.getRandomCategory());
        }
    }

    @Override
    public List<ProductPriceSellerNameDto> findAllProductsInPriceRange(BigDecimal lower, BigDecimal upper) {
        List<Product> products = productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(lower, upper);
        return products.stream()
                .map(product -> {
                    ProductPriceSellerNameDto productPriceSellerNameDto = modelMapper.map(product, ProductPriceSellerNameDto.class);
                    productPriceSellerNameDto.setSeller(product.getSeller().getFirstName() != null
                            ? product.getSeller().getFirstName() +" "+ product.getSeller().getLastName()
                            : product.getSeller().getLastName());
                    return productPriceSellerNameDto;
                })
                .collect(Collectors.toList());
    }
}
