package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.TeamRootDto;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;

    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository,
                           XmlParser xmlParser, ModelMapper modelMapper,
                           ValidatorUtil validatorUtil, FileUtil fileUtil) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public String importTeams() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        TeamRootDto teamRootDto = xmlParser.fromFile("src/main/resources/files/xml/teams.xml", TeamRootDto.class);
        teamRootDto.getTeams()
                .stream().filter(teamDto -> {
            boolean isValid = validatorUtil.isValid(teamDto)
                    && !teamRepository.existsByName(teamDto.getName())
                    && pictureRepository.existsByUrl(teamDto.getPicture().getUrl());
            sb.append(isValid ? String.format("Successfully imported - %s", teamDto.getName())
                    : "Invalid team").append(System.lineSeparator());
            return isValid;
        }).map(teamDto -> {
            Team team = modelMapper.map(teamDto, Team.class);
            team.setPicture(pictureRepository.findByUrl(teamDto.getPicture().getUrl()));
            return team;
        }).forEach(teamRepository::save);

        return sb.toString();
    }

    @Override
    public boolean areImported() {

        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {

        return fileUtil.readFile("src/main/resources/files/xml/teams.xml");
    }
}
