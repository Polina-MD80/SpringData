package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import softuni.exam.models.dto.PlaneRootDto;
import softuni.exam.models.entity.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.validation.constraints.Size;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;

    public PlaneServiceImpl(XmlParser xmlParser, ValidationUtil validationUtil,
                            PlaneRepository planeRepository, ModelMapper modelMapper) {
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/planes.xml"));
    }

    @Override
    public String importPlanes() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        PlaneRootDto planeRootDto = xmlParser.fromFile("src/main/resources/files/xml/planes.xml", PlaneRootDto.class);
        planeRootDto.getPlanes().stream()
                .filter(planeDto ->
                {
                    boolean isValid = validationUtil.isValid(planeDto)
                            &&!planeIsInDataBase(planeDto.getRegisterNumber());

                    sb.append(isValid ? String.format("Successfully imported Plane %s",
                            planeDto.getRegisterNumber())
                            : "Invalid plane").append(System.lineSeparator());

                    return isValid;
                })
                .map(planeDto -> modelMapper.map(planeDto, Plane.class))
                .forEach(planeRepository::save);
        return sb.toString();
    }

    private boolean planeIsInDataBase(String registerNumber) {
        return planeRepository.existsByRegisterNumber(registerNumber);
    }
}
