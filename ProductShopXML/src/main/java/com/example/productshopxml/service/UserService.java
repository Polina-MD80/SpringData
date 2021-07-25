package com.example.productshopxml.service;

import com.example.productshopxml.model.dto.seedDtos.UserSeedDto;
import com.example.productshopxml.model.dto.usersAndProducts.UserProductRootDto;
import com.example.productshopxml.model.dto.usersAndProducts.UserProductsDto;
import com.example.productshopxml.model.dto.usersSoldProducts.UserSoldProductsRootDto;
import com.example.productshopxml.model.entity.User;

import java.util.List;

public interface UserService {

    void seedUsers(List<UserSeedDto> userSeedDtos);

    User getRandomUser();

    UserSoldProductsRootDto getUsersSoldProducts();

    UserProductRootDto getUserProducts();

}
