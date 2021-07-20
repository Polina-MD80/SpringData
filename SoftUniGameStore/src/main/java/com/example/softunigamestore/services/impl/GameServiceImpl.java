package com.example.softunigamestore.services.impl;

import com.example.softunigamestore.models.dto.GameAddDto;
import com.example.softunigamestore.models.dto.GameFullInfoDto;
import com.example.softunigamestore.models.dto.GameTitlePriceDto;
import com.example.softunigamestore.models.entities.Game;
import com.example.softunigamestore.repositories.GameRepository;
import com.example.softunigamestore.services.GameService;
import com.example.softunigamestore.services.UserService;
import com.example.softunigamestore.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {
      if (!this.userService.hasLoggedInUser()){
          System.out.println("There is no logged user, login user before adding game.");
         return;
      }
        Set<ConstraintViolation<GameAddDto>> violations = validationUtil.violation(gameAddDto);

        if (!violations.isEmpty()){
            violations.stream().map(ConstraintViolation::getMessage).forEach(System.out::println);
            return;
        }

        Game game = modelMapper.map(gameAddDto, Game.class);
       // game.setReleaseDate(LocalDate.parse(gameAddDto.getReleaseDate(), DateTimeFormatter.ofPattern("dd-MM-yyy")));
        //this.gameRepository.save(game);
        this.userService.addGameToUser(game);
        System.out.printf("Added %s%n", game.getTitle());

    }

    @Override
    public void updateGame(long id, BigDecimal price, double size) {
        Game game = this.gameRepository.findById(id).orElse(null);
        if (game == null){
            System.out.println("Invalid game id");
            return;
        }
        game.setPrice(price);
        game.setSize(size);
        this.gameRepository.save(game);
        System.out.println("Edited " + game.getTitle());

    }

    @Override
    public void deleteGame(long id) {
        Game game = this.gameRepository.findById(id).orElse(null);
        if (game == null){
            System.out.println("Invalid game id");
            return;
        }
        this.gameRepository.delete(game);
        System.out.println("Deleted " + game.getTitle());

    }

    @Override
    public List<String> listAllGames() {
        List<Game>games= this.gameRepository.findAll();
        List<GameTitlePriceDto> gamesView = games.stream().map(game-> modelMapper.map(game,GameTitlePriceDto.class)).collect(Collectors.toList());
        return gamesView.stream().map(GameTitlePriceDto::toString).collect(Collectors.toList());
    }

    @Override
    public String giveGameDetails(String name) {
        Game game =  this.gameRepository.findByTitle(name);
        GameFullInfoDto gameInfo = modelMapper.map(game, GameFullInfoDto.class);
        return gameInfo.toString();
    }
}
