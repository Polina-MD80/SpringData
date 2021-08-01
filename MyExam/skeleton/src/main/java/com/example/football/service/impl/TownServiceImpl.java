package com.example.football.service.impl;

import com.example.football.models.dto.TownDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.FileUtil;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;


@Service
public class TownServiceImpl implements TownService {
    private final Gson gson;
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private static final String TEAM_XML = "src/main/resources/files/json/towns.json";

    public TownServiceImpl(Gson gson, TownRepository townRepository,
                           ValidationUtil validationUtil, ModelMapper modelMapper, FileUtil fileUtil) {
        this.gson = gson;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
    }


    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return fileUtil.readFile(TEAM_XML);
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readTownsFileContent(), TownDto[].class))
                .filter(townDto -> {

                    boolean isValid = validationUtil.isValid(townDto)
                            && !townRepository.existsByName(townDto.getName());

                    sb.append(isValid ? String.format("Successfully imported Town %s - %d",
                            townDto.getName(), townDto.getPopulation())
                            : "Invalid Town").append(System.lineSeparator());

                    return isValid;
                })
                .map(townDto -> modelMapper.map(townDto, Town.class))
                .forEach(townRepository::save);

        return sb.toString();
    }
}
