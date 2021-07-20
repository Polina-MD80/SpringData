package com.example.productsshop.service.impl;

import com.example.productsshop.model.dto.*;
import com.example.productsshop.model.entity.User;
import com.example.productsshop.repository.UserRepository;
import com.example.productsshop.service.UserService;
import com.example.productsshop.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.productsshop.constants.GlobalConstants.FILE_PATH;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private static final String USER_FILE_NAME = "users.json";

    public UserServiceImpl(UserRepository userRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers() throws IOException {
        if (userRepository.count() > 0) {
            return;
        }
        String data = Files.readString(Path.of(FILE_PATH + USER_FILE_NAME));

        UserSeedDto[] userSeedDtos = gson.fromJson(data, UserSeedDto[].class);

        Arrays.stream(userSeedDtos)
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
    public List<UserSellerDto> findAllSellersAndTheirSoldProducts() {
        List<User> users = userRepository.findAllBySoldProductsMoreThanZero();
        return users.stream()
                .map(user -> modelMapper.map(user, UserSellerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsersCountDto getUsersAndProductsWithCount() {
        List<User> users = this.userRepository.findAllUsersWithSoldProductsOrderedByNumberOfProductsDescAndLastName();
        UsersCountDto usersCountDto = new UsersCountDto();
        List<UsersAndProductsDto> usersAndProductsDtos = users.stream().map(user -> {
            UsersAndProductsDto userDto = modelMapper.map(user, UsersAndProductsDto.class);
            List<ProductNameAndPriceDto> productDtos = user.getSoldProducts().stream().map(product -> modelMapper.map(product, ProductNameAndPriceDto.class)).collect(Collectors.toList());
            Products products = new Products();
            products.setProducts(productDtos);
            products.setCount(productDtos.size());
            userDto.setListOfSoldProducts(products);

            return userDto;
        }
    ).collect(Collectors.toList());
        usersCountDto.setUsers(usersAndProductsDtos);
        usersCountDto.setUsersCount(users.size());
        return usersCountDto;
}
}