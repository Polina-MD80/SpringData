package com.example.productsshop.service;

import com.example.productsshop.model.dto.UserSellerDto;
import com.example.productsshop.model.dto.UsersCountDto;
import com.example.productsshop.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User getRandomUser();

    List<UserSellerDto> findAllSellersAndTheirSoldProducts();

    UsersCountDto getUsersAndProductsWithCount();
}
