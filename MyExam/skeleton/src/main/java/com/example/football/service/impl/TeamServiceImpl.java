package com.example.football.service.impl;

import com.example.football.models.dto.TeamDto;
import com.example.football.models.entity.Team;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.FileUtil;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class TeamServiceImpl implements TeamService {
    private final Gson gson;
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final TeamRepository teamRepository;
    private static final String TEAM_JSON = "src/main/resources/files/json/teams.json";

    public TeamServiceImpl(Gson gson, TownRepository townRepository,
                           ValidationUtil validationUtil, ModelMapper modelMapper,
                           FileUtil fileUtil, TeamRepository teamRepository) {
        this.gson = gson;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.teamRepository = teamRepository;
    }


    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return fileUtil.readFile(TEAM_JSON);
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readTeamsFileContent(), TeamDto[].class))
                .filter(teamDto -> {

                    boolean isValid = validationUtil.isValid(teamDto)
                            && !teamRepository.existsByName(teamDto.getName())
                            && townRepository.existsByName(teamDto.getTownName());

                    sb.append(isValid ? String.format("Successfully imported Team %s - %d", teamDto.getName(), teamDto.getFanBase())
                            : "Invalid Team").append(System.lineSeparator());

                    return isValid;
                })
                .map(teamDto -> {

                    Team team = modelMapper.map(teamDto, Team.class);
                    team.setTown(townRepository.findByName(teamDto.getTownName()));

                    return team;
                })
                .forEach(teamRepository::save);

        return sb.toString();
    }
}
