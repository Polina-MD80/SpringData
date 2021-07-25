package com.example.productshopxml.service.impl;

import com.example.productshopxml.model.dto.productsInRangeDtos.ProductInRangeDto;
import com.example.productshopxml.model.dto.productsInRangeDtos.ProductInRangeRootDto;
import com.example.productshopxml.model.dto.seedDtos.ProductSeedDto;
import com.example.productshopxml.model.entity.Product;
import com.example.productshopxml.repository.ProductRepository;
import com.example.productshopxml.service.CategoryService;
import com.example.productshopxml.service.ProductService;
import com.example.productshopxml.service.UserService;
import com.example.productshopxml.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ValidationUtil validationUtil,
                              ModelMapper modelMapper, ProductRepository productRepository, UserService userService, CategoryService categoryService) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Override
    public void seedProducts(List<ProductSeedDto> productSeedDtos) {
        if (productRepository.count() > 0) {
            return;
        }
        productSeedDtos.stream()
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    if (product.getName().charAt(0) != 'E') {
                        product.setBuyer(this.userService.getRandomUser());
                    }
                    product.setSeller(userService.getRandomUser());
                    setCategories(product);
                    return product;
                })
                .forEach(productRepository::save);
    }

    @Override
    public ProductInRangeRootDto getProductsInRange(BigDecimal low, BigDecimal high) {
        List<Product> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(low, high);
        List<ProductInRangeDto> productInRangeDtos = products.stream()
                .map(product -> {
                    ProductInRangeDto productInRangeDto = modelMapper.map(product, ProductInRangeDto.class);
                    productInRangeDto.setSeller(product.getSeller().getFirstName() == null
                            ? product.getSeller().getLastName()
                            : String.format("%s %s", product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));

                    return productInRangeDto;
                })
                .collect(Collectors.toList());

        ProductInRangeRootDto productInRangeRootDto = new ProductInRangeRootDto();
        productInRangeRootDto.setProductInRangeDtos(productInRangeDtos);

        return productInRangeRootDto;
    }

    private void setCategories(Product product) {
        int numberOfCategories = ThreadLocalRandom.current().nextInt(1, 4);
        for (int i = 0; i < numberOfCategories; i++) {
            product.getCategories().add(categoryService.getRandomCategory());
        }
    }
}
