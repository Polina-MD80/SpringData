package com.example.cardealer.service;

import com.example.cardealer.model.entity.Car;

import java.io.IOException;

public interface CarService {

    void seedData() throws IOException;

    Car getRandomCar();
}
