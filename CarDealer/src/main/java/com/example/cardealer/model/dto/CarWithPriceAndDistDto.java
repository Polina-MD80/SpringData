package com.example.cardealer.model.dto;

import com.example.cardealer.model.entity.Part;
import com.google.gson.annotations.Expose;

import java.util.List;

public class CarWithPriceAndDistDto {
    @Expose
    private String Make;
    @Expose
    private String Model;
    @Expose
    private Long TravelledDistance;
    private List<Part> parts;

    public CarWithPriceAndDistDto() {
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public Long getTravelledDistance() {
        return TravelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        TravelledDistance = travelledDistance;
    }
}
