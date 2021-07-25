package com.example.productshopxml.service.impl;

import com.example.productshopxml.model.dto.seedDtos.UserSeedDto;
import com.example.productshopxml.model.dto.usersAndProducts.ProductNameAndPriceDto;
import com.example.productshopxml.model.dto.usersAndProducts.SoldProductsPerUserRootDto;
import com.example.productshopxml.model.dto.usersAndProducts.UserProductRootDto;
import com.example.productshopxml.model.dto.usersAndProducts.UserProductsDto;
import com.example.productshopxml.model.dto.usersSoldProducts.ProductInfoAndBuyerDto;
import com.example.productshopxml.model.dto.usersSoldProducts.SoldProductsRootDto;
import com.example.productshopxml.model.dto.usersSoldProducts.UserNamesSoldProductsDto;
import com.example.productshopxml.model.dto.usersSoldProducts.UserSoldProductsRootDto;
import com.example.productshopxml.model.entity.User;
import com.example.productshopxml.repository.UserRepository;
import com.example.productshopxml.service.UserService;
import com.example.productshopxml.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedUsers(List<UserSeedDto> userSeedDtos) {
        if (userRepository.count() > 0) {
            return;
        }
        userSeedDtos.stream()
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, userRepository.count() + 1);
        return userRepository
                .findById(randomId)
                .orElse(null);
    }

    @Override
    public UserSoldProductsRootDto getUsersSoldProducts() {
        List<User> users = userRepository.findAllBySoldProductsMoreThanZero();

        List<UserNamesSoldProductsDto> userNamesSoldProductsDtos = users.stream()
                .map(user -> {
                    UserNamesSoldProductsDto userNamesSoldProductsDto = modelMapper.map(user, UserNamesSoldProductsDto.class);
                    List<ProductInfoAndBuyerDto> productInfoAndBuyerDtos = user.getSoldProducts().stream()
                            .filter(product -> product.getBuyer() != null)
                            .map(product -> {
                                ProductInfoAndBuyerDto productInfoAndBuyerDto = modelMapper.map(product, ProductInfoAndBuyerDto.class);
                                productInfoAndBuyerDto.setBuyerFirstName(product.getBuyer().getFirstName());
                                productInfoAndBuyerDto.setBuyerLastName(product.getBuyer().getLastName());
                                return productInfoAndBuyerDto;
                            }).collect(Collectors.toList());
                    SoldProductsRootDto soldProductsRootDto = new SoldProductsRootDto();
                    soldProductsRootDto.setProducts(productInfoAndBuyerDtos);
                    userNamesSoldProductsDto.setProductsRootDto(soldProductsRootDto);
                    return userNamesSoldProductsDto;
                }).collect(Collectors.toList());


        UserSoldProductsRootDto userSoldProductsRootDto = new UserSoldProductsRootDto();
        userSoldProductsRootDto.setUsers(userNamesSoldProductsDtos);
        return userSoldProductsRootDto;
    }

    @Override
    public UserProductRootDto getUserProducts() {
        List<User> users = userRepository.findAllUsersWithSoldProductsOrderedByNumberOfProductsDescAndLastName();
        UserProductRootDto userProductRootDto = new UserProductRootDto();
        userProductRootDto.setCount(users.size());

        List<UserProductsDto> userProductsDtos = users.stream().map(user -> {
                    UserProductsDto userProductsDto = modelMapper.map(user, UserProductsDto.class);
                    userProductsDto.setSoldProducts(new SoldProductsPerUserRootDto());
                    List<ProductNameAndPriceDto> productNameAndPriceDtos = user.getSoldProducts().stream()
                            .map(product -> modelMapper.map(product, ProductNameAndPriceDto.class)).collect(Collectors.toList());
                    userProductsDto.getSoldProducts().setSoldProducts(productNameAndPriceDtos);
                    userProductsDto.getSoldProducts().setCount(productNameAndPriceDtos.size());

                    return userProductsDto;
                }
        ).collect(Collectors.toList());

        userProductRootDto.setUsers(userProductsDtos);

        return userProductRootDto;
    }

}
