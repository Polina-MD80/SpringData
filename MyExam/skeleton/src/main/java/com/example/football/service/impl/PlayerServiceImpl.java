package com.example.football.service.impl;

import com.example.football.models.dto.playerinfo.PlayerDto;
import com.example.football.models.dto.playerinfo.PlayerExportDto;
import com.example.football.models.dto.playerinfo.PlayerRootDto;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.FileUtil;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final StatRepository statRepository;
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final PlayerRepository playerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private static final String PLAYER_XML = "src/main/resources/files/xml/players.xml";

    public PlayerServiceImpl(StatRepository statRepository, TeamRepository teamRepository,
                             TownRepository townRepository, PlayerRepository playerRepository,
                             XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil,
                             FileUtil fileUtil) {
        this.statRepository = statRepository;
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.playerRepository = playerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return fileUtil.readFile(PLAYER_XML);
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        xmlParser.fromFile(PLAYER_XML, PlayerRootDto.class)
                .getPlayers()
                .stream()
                .filter(playerDto -> {

                    boolean isValid = validationUtil.isValid(playerDto)
                            && !playerRepository.existsByEmail(playerDto.getEmail());

                    sb.append(isValid ? String.format("Successfully imported Player %s %s - %s",
                            playerDto.getFirstName(), playerDto.getLastName(), playerDto.getPosition().toString())
                            : "Invalid Player").append(System.lineSeparator());

                    return isValid;
                })
                .map(playerDto -> {
                    Player player = modelMapper.map(playerDto, Player.class);
                    setAllFields(player, playerDto);
                    return player;
                })
                .forEach(playerRepository::save);

        return sb.toString();
    }

    private void setAllFields(Player player, PlayerDto playerDto) {
        player.setStat(statRepository.findStatById(playerDto.getStat().getId()));
        player.setTeam(teamRepository.findByName(playerDto.getTeam().getName()));
        player.setTown(townRepository.findByName(playerDto.getTown().getName()));
    }

    @Override
    public String exportBestPlayers() {

        StringBuilder sb = new StringBuilder();

        List<Player> players = playerRepository
                .exportTheBestPlayers(LocalDate.of(1995, 1, 1),
                        LocalDate.of(2003, 1, 1));

        Arrays.stream(modelMapper.map(players, PlayerExportDto[].class))
                .forEach(playerExportDto -> sb.append(playerExportDto.toString()).append(System.lineSeparator()));

        return sb.toString();
    }
}
