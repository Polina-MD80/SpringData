package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferSeedDto;
import softuni.exam.models.dto.OfferSeedRootDto;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final CarService carService;
    private final SellerService sellerService;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository,
                            XmlParser xmlParser, ValidationUtil validationUtil, CarService carService, SellerService sellerService, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.carService = carService;
        this.sellerService = sellerService;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/offers.xml"));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        OfferSeedRootDto offerSeedRootDto = xmlParser.fromFile("src/main/resources/files/xml/offers.xml", OfferSeedRootDto.class);
        offerSeedRootDto.getOffers()
                .stream()
                .filter(offerSeedDto -> {
                    boolean isValid = validationUtil.isValid(offerSeedDto);
                    sb.append(isValid ? String.format("Successfully import offer %s - %s", offerSeedDto.getAddedOn(), offerSeedDto.getHasGoldStatus())
                            : "Invalid offer")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(offerSeedDto -> {
                    Offer offer = modelMapper.map(offerSeedDto, Offer.class);
                    setRestOfFields(offer,offerSeedDto);

                    return offer;
                })
                .forEach(offerRepository :: save);

        return sb.toString();
    }

    private void setRestOfFields(Offer offer, OfferSeedDto offerSeedDto) {
      offer.setCar(carService.findById(offerSeedDto.getCar().getId()));
      offer.setSeller(sellerService.findById(offerSeedDto.getSeller().getId()));


    }
}
