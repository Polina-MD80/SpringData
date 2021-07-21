package com.example.cardealer.model.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CarsAndParts {

    @Expose
    private CarWithInfoAndParts car;

    public CarsAndParts() {
    }

    public CarWithInfoAndParts getCar() {
        return car;
    }

    public void setCar(CarWithInfoAndParts car) {
        this.car = car;
    }
}
