package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.common.GlobalConstants;
import hiberspring.domain.dto.TownDto;
import hiberspring.domain.entity.Town;
import hiberspring.repository.TownRepository;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static hiberspring.common.GlobalConstants.*;

@Service
public class TownServiceImpl implements TownService {
   private final TownRepository townRepository;
   private final ModelMapper modelMapper;
   private final Gson gson;
   private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return townRepository.count()>0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns(String townsFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();
        TownDto[] townDtos = gson.fromJson(readTownsJsonFile(), TownDto[].class);
        Arrays.stream(townDtos).filter(townDto -> {
            boolean isValid = validationUtil.isValid(townDto)
                    && !townRepository.existsByName(townDto.getName());
            sb.append(isValid ? String.format(SUCCESSFUL_IMPORT_MESSAGE, "town", townDto.getName())
                    : INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
            return isValid;
        })
                .map(townDto -> modelMapper.map(townDto, Town.class))
                .forEach(townRepository::save);
        return sb.toString();
    }
}
