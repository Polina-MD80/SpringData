package com.example.productshopxml.service;

import com.example.productshopxml.model.dto.UserSeedDto;
import com.example.productshopxml.model.dto.UserSoldProductsRootDto;
import com.example.productshopxml.model.entity.User;

import java.util.List;

public interface UserService {
    void seedUsers(List<UserSeedDto> userSeedDtos);
    User getRandomUser();

    UserSoldProductsRootDto getUsersSoldProducts();
}
