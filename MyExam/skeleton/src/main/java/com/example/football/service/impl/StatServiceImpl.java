package com.example.football.service.impl;

import com.example.football.models.dto.StatRootDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.FileUtil;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private static final String STAT_XML = "src/main/resources/files/xml/stats.xml";

    public StatServiceImpl(StatRepository statRepository, XmlParser xmlParser,
                           ModelMapper modelMapper, ValidationUtil validationUtil, FileUtil fileUtil) {
        this.statRepository = statRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
    }


    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return fileUtil.readFile(STAT_XML);
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        xmlParser.fromFile(STAT_XML, StatRootDto.class)
                .getStats()
                .stream().filter(statDto -> {

            boolean isValid = validationUtil.isValid(statDto)
                    && !statRepository.existsByPassingAndShootingAndEndurance(statDto.getPassing(),
                    statDto.getShooting(), statDto.getEndurance());

            sb.append(isValid ? String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                    statDto.getShooting(), statDto.getPassing(), statDto.getEndurance())
                    : "Invalid Stat").append(System.lineSeparator());

            return isValid;
        })
                .map(statDto -> modelMapper.map(statDto, Stat.class))
                .forEach(statRepository::save);

        return sb.toString();
    }
}
