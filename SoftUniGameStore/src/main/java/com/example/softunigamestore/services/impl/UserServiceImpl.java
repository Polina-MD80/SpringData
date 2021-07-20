package com.example.softunigamestore.services.impl;

import com.example.softunigamestore.models.dto.UserLoginDto;
import com.example.softunigamestore.models.dto.UserRegisterDto;
import com.example.softunigamestore.models.entities.BaseEntity;
import com.example.softunigamestore.models.entities.Game;
import com.example.softunigamestore.models.entities.User;
import com.example.softunigamestore.repositories.UserRepository;
import com.example.softunigamestore.services.UserService;
import com.example.softunigamestore.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private User loggedInUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            System.out.println("Wrong confirm password");
            return;
        }

        Set<ConstraintViolation<UserRegisterDto>> violations =
                validationUtil.violation(userRegisterDto);
        if (!violations.isEmpty()) {
            violations.stream().map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }
        User user = modelMapper.map(userRegisterDto, User.class);
        if (this.userRepository.findAll().isEmpty()) {
            user.setAdmin(true);
        }
        try {

            userRepository.save(user);
        }catch (Throwable e){
            System.out.println("User is already registered.");
            return;
        }
        System.out.println(user.getFullName() + " was registered");

    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        if (loggedInUser != null) {
            System.out.println("Another user is logged at the moment, try to login later.");
        }
        Set<ConstraintViolation<UserLoginDto>> violations = validationUtil.violation(userLoginDto);
        if (!violations.isEmpty()) {
            violations.stream().map(ConstraintViolation::getMessage).forEach(System.out::println);
            return;
        }

        var user = this.userRepository.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword());
        if (user == null) {
            System.out.println("Incorrect username / password");
            return;
        }
        loggedInUser = user;
        System.out.println("Successfully logged in Ivan");
    }

    @Override
    public void logoutUser() {
        if (loggedInUser == null) {
            System.out.println("Cannot log out. No user was logged in.");
            return;
        }

        String fullName = loggedInUser.getFullName();
        loggedInUser = null;
        System.out.printf("User %s successfully logged out%n", fullName);

    }

    @Override
    public Boolean hasLoggedInUser() {
        return loggedInUser != null;
    }

    @Override
    public List<String> getUserGames() {
        if (!hasLoggedInUser()){
          return List.of("There is no logged in user.");
        }
        return this.loggedInUser.getGames().stream().map(Game::getTitle).collect(Collectors.toList());
    }

    @Override
    public void addGameToUser(Game game) {
        this.loggedInUser.purchase(game);
    }


}
