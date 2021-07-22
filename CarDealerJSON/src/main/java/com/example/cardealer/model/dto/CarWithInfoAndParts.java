package com.example.cardealer.model.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CarWithInfoAndParts {
  @Expose
    private String Make;
    @Expose
    private String Model;
    @Expose
    private Long TravelledDistance;
    @Expose
    private List<PartNameAndPriceDto> parts;

    public CarWithInfoAndParts() {
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

    public List<PartNameAndPriceDto> getParts() {
        return parts;
    }

    public void setParts(List<PartNameAndPriceDto> parts) {
        this.parts = parts;
    }
}
