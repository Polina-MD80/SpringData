package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PassengerDto;
import softuni.exam.models.entity.Passenger;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, TownRepository townRepository) {
        this.passengerRepository = passengerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/passengers.json"));
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder sb = new StringBuilder();
        PassengerDto[] passengerDtos = gson.fromJson(readPassengersFileContent(), PassengerDto[].class);
        Arrays.stream(passengerDtos)
                .filter(passengerDto -> {
                    boolean isValid = validationUtil.isValid(passengerDto)
                            &&!passengerIsInDataBase(passengerDto.getEmail())
                            &&townRepository.existsByName(passengerDto.getTown());

                    sb.append(isValid ? String.format("Successfully imported Passenger %s - %s",
                            passengerDto.getLastName(), passengerDto.getEmail())
                            : "Invalid passenger").append(System.lineSeparator());
                    return isValid;
                })
                .map(passengerDto -> {
                    Passenger passenger = modelMapper.map(passengerDto, Passenger.class);
                    passenger.setTown(townRepository.getTownByName(passengerDto.getTown()));
                    return passenger;
                })
                .forEach(passengerRepository::save);
        return sb.toString();
    }

    private boolean passengerIsInDataBase(String email) {
        return passengerRepository.existsByEmail(email);
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        StringBuilder sb = new StringBuilder();
        List<Passenger> passengers = passengerRepository.findOrderedPassengers();
        passengers.forEach(passenger -> sb.append(passenger.toString()).append(System.lineSeparator()));
        return sb.toString();
    }

    @Override
    public Passenger findByEmail(String email) {

     return    passengerRepository.findByEmail(email);
    }
}
