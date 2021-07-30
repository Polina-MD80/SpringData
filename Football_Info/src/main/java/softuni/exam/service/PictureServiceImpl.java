package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.PictureRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil, FileUtil fileUtil) {
        this.pictureRepository = pictureRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public String importPictures() throws JAXBException, FileNotFoundException {
       StringBuilder sb = new StringBuilder();
        PictureRootDto pictureRootDto = xmlParser.fromFile("src/main/resources/files/xml/pictures.xml", PictureRootDto.class);
        pictureRootDto.getPictures()
                .stream()
                .filter(pictureDto -> {
                    boolean isValid = validatorUtil.isValid(pictureDto)
                            &&!pictureRepository.existsByUrl(pictureDto.getUrl());
                    sb.append(isValid ? String.format("Successfully imported picture - %s", pictureDto.getUrl())
                            : "invalid picture").append(System.lineSeparator());
                    return isValid;
                })
                .map(pictureDto -> modelMapper.map(pictureDto, Picture.class))
                .forEach(pictureRepository::save);

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return fileUtil.readFile("src/main/resources/files/xml/pictures.xml");
    }

}
