package com.example.cardealer.service.impl;

import com.example.cardealer.model.dto.*;
import com.example.cardealer.model.entity.Car;
import com.example.cardealer.model.entity.Part;
import com.example.cardealer.repository.CarRepository;
import com.example.cardealer.service.CarService;
import com.example.cardealer.service.PartService;
import com.example.cardealer.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.cardealer.constants.GlobalApplicationConstants.FILE_PATH_READ;

@Service
public class CarServiceImpl implements CarService {
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final PartService partService;

    public CarServiceImpl(Gson gson, ValidationUtil validationUtil,
                          ModelMapper modelMapper, CarRepository carRepository,
                          PartService partService) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.partService = partService;
    }


    @Override
    public void seedData() throws IOException {
        if (carRepository.count() > 0) {
            return;
        }
        String data = Files.readString(Path.of(FILE_PATH_READ + "cars.json"));

        CarSeedDto[] carSeedDtos = gson.fromJson(data, CarSeedDto[].class);

        Arrays.stream(carSeedDtos)
                .filter(validationUtil::isValid)
                .map(carSeedDto -> {
                    Car car = modelMapper.map(carSeedDto, Car.class);
                    List<Part> partList = getListOfRandomParts();
                    car.setParts(partList);
                    return car;

                })
                .forEach(carRepository::save);
    }

    @Override
    public Car getRandomCar() {
        long count = this.carRepository.count();
        Long randomId = ThreadLocalRandom.current().nextLong(1, count + 1);
        return this.carRepository.getById(randomId);
    }

    @Override
    public List<CarsToyotaDto> getCarsFromMakeToyota(String make) {
        List<Car> cars = this.carRepository.findCarByMakeOrderByModel(make);
        List<CarsToyotaDto> carsToyotaDtos = cars.stream()
                .sorted((f, s) -> Long.compare(s.getTravelledDistance(), f.getTravelledDistance()))
                .map(car -> modelMapper.map(car, CarsToyotaDto.class))
                .collect(Collectors.toList());
        return carsToyotaDtos;
    }

    @Override
    public List<CarsAndParts> getCarsAndParts() {
        List<Car> cars = carRepository.findAll();
        List<CarsAndParts> carsAndParts = cars.stream()
                .map(car -> modelMapper.map(car, CarsAndParts.class))
                .collect(Collectors.toList());

        return carsAndParts;


    }


    private List<Part> getListOfRandomParts() {
        List<Part> parts = new ArrayList<>();
        int numberOfParts = ThreadLocalRandom.current().nextInt(3, 6);
        for (int i = 0; i < numberOfParts; i++) {
            parts.add(partService.getRandomPart());
        }
        return parts;
    }
}
