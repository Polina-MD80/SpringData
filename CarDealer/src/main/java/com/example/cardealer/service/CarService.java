package com.example.cardealer.service;

import com.example.cardealer.model.dto.CarsAndParts;
import com.example.cardealer.model.dto.CarsToyotaDto;
import com.example.cardealer.model.entity.Car;

import java.io.IOException;
import java.util.List;

public interface CarService {

    void seedData() throws IOException;

    Car getRandomCar();

    List<CarsToyotaDto> getCarsFromMakeToyota(String make);

    List<CarsAndParts> getCarsAndParts();
}
