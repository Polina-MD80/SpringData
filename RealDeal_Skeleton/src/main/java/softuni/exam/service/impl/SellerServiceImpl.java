package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;


import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SellerServiceImpl implements SellerService {

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final SellerRepository sellerRepository;
    private final XmlParser xmlParser;
    public SellerServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper, SellerRepository sellerRepository, XmlParser xmlParser) {

        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.sellerRepository = sellerRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/sellers.xml"));

    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        SellerSeedRootDto sellerSeedRootDto = xmlParser.fromFile("src/main/resources/files/xml/sellers.xml", SellerSeedRootDto.class);

        sellerSeedRootDto.getSellers()
                .stream()
                .filter(sellerSeedDto -> {
                    boolean isValid = validationUtil.isValid(sellerSeedDto);
                    sb.append(isValid ? String.format("Successfully import seller %s - %s", sellerSeedDto.getLastName(),
                            sellerSeedDto.getEmail()) : "Invalid seller")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(sellerSeedDto -> modelMapper.map(sellerSeedDto, Seller.class))
                .forEach(sellerRepository :: save);
        return sb.toString();
    }

    @Override
    public Seller findById(Long id) {
      return   sellerRepository.findById(id).orElse(null);
    }
}
