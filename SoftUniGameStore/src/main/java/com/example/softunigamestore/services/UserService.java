package com.example.softunigamestore.services;

import com.example.softunigamestore.models.dto.UserLoginDto;
import com.example.softunigamestore.models.dto.UserRegisterDto;
import com.example.softunigamestore.models.entities.Game;

import java.util.List;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logoutUser();

    Boolean hasLoggedInUser();

    List<String> getUserGames();

    void addGameToUser(Game game);
}
