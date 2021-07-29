package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository,
                           ModelMapper modelMapper, Gson gson,
                           ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/towns.json"));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();
        TownDto[] townDtos = gson.fromJson(readTownsFileContent(), TownDto[].class);
        Arrays.stream(townDtos)
                .filter(townDto -> {
                    boolean isValid = validationUtil.isValid(townDto)
                            &&!townIsInDataBase(townDto.getName());
                    sb.append(isValid ? String.format("Successfully imported Town %s - %d,",
                            townDto.getName(),townDto.getPopulation())
                            : "Invalid town")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(townDto -> modelMapper.map(townDto, Town.class))
                .forEach(townRepository::save);
        return sb.toString();
    }

    @Override
    public Town findByTownName(String town) {
        return townRepository.getTownByName(town);
    }

    private boolean townIsInDataBase(String name) {
        return townRepository.existsByName(name);
    }
}
