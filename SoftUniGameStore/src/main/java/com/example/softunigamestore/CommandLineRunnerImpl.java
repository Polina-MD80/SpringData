package com.example.softunigamestore;

import com.example.softunigamestore.models.dto.GameAddDto;
import com.example.softunigamestore.models.dto.UserLoginDto;
import com.example.softunigamestore.models.dto.UserRegisterDto;
import com.example.softunigamestore.services.GameService;
import com.example.softunigamestore.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final BufferedReader reader;
    private final UserService userService;
    private final GameService gameService;

    public CommandLineRunnerImpl(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            System.out.println("Enter commands:");

            String[] commands = reader.readLine().split("\\|");

            switch (commands[0]) {
                case "RegisterUser" -> userService.registerUser(new UserRegisterDto(commands[1],
                        commands[2], commands[3], commands[4]));
                case "LoginUser" -> userService.loginUser(new UserLoginDto(commands[1], commands[2]));
                //some more notifications for invalid email and password added//
                case "Logout" -> userService.logoutUser();
                case "AddGame" -> gameService.addGame(
                        new GameAddDto(commands[1], new BigDecimal(commands[2]),
                                Double.parseDouble(commands[3]), commands[4],
                                commands[5], commands[6], commands[7])
                );
                case "EditGame" -> gameService.updateGame(Long.parseLong(commands[1]), new BigDecimal(commands[2].substring(6)),
                        Double.parseDouble(commands[3].substring(5)));
                case "DeleteGame" -> gameService.deleteGame(Long.parseLong(commands[1]));
                case "AllGames" -> gameService.listAllGames().forEach(System.out::println);
                case "DetailGame" -> System.out.println(gameService.giveGameDetails(commands[1]));
                case "OwnedGames" -> this.userService.getUserGames().forEach(System.out::println);
                default -> System.out.println("Invalid command!");
            }
        }

    }


}
