package com.example.softunigamestore.services;

import com.example.softunigamestore.models.dto.GameAddDto;

import java.math.BigDecimal;
import java.util.List;

public interface GameService {
    void addGame(GameAddDto gameAddDto);

    void updateGame(long id, BigDecimal price, double size);

    void deleteGame(long id);

    List<String> listAllGames();

    String giveGameDetails(String name);
}
