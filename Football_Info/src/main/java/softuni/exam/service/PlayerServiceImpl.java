package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.PlayerDto;
import softuni.exam.domain.dto.PlayerOutDto;
import softuni.exam.domain.dto.PlayerWithSalaryDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository,
                             PictureRepository pictureRepository, Gson gson, ModelMapper modelMapper, ValidatorUtil validatorUtil, FileUtil fileUtil) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public String importPlayers() throws IOException {
        StringBuilder sb = new StringBuilder();
        PlayerDto[] playerDtos = gson.fromJson(readPlayersJsonFile(), PlayerDto[].class);
       Arrays.stream(playerDtos)
                .filter(playerDto -> {
                    boolean isValid = validatorUtil.isValid(playerDto)
                            && pictureRepository.existsByUrl(playerDto.getPicture().getUrl())
                            && teamRepository.existsByName(playerDto.getTeam().getName())
                            && pictureRepository.existsByUrl(playerDto.getTeam().getPicture().getUrl());

                    sb.append(isValid ? String.format("Successfully imported player: %s %s", playerDto.getFirstName(), playerDto.getLastName())
                            : "Invalid player").append(System.lineSeparator());

                    return isValid;
                }).map(playerDto ->{

                    Player player = modelMapper.map(playerDto, Player.class);
                    player.setPicture(pictureRepository.findByUrl(playerDto.getPicture().getUrl()));
                    player.setTeam(teamRepository.findByName(playerDto.getTeam().getName()));
                    return player;
                })
                .forEach(playerRepository::save);

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return fileUtil.readFile("src/main/resources/files/json/players.json");
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();
        List<Player> players = playerRepository.findAllBySalaryGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000));
        PlayerWithSalaryDto[] playerWithSalaryDtos = modelMapper.map(players, PlayerWithSalaryDto[].class);
        Arrays.stream(playerWithSalaryDtos).forEach(playerWithSalaryDto->sb.append(playerWithSalaryDto.toString()).append(System.lineSeparator()));
        return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder("Team North Hub");

        List<Player> players = playerRepository.findByTeam("North Hub");
        PlayerOutDto[] outDtos = modelMapper.map(players, PlayerOutDto[].class);
        Arrays.stream(outDtos).forEach(outDto-> sb.append(System.lineSeparator()).append(outDto.toString()));
        return sb.toString();
    }
}
