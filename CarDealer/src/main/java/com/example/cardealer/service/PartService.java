package com.example.cardealer.service;

import com.example.cardealer.model.entity.Part;

import java.io.IOException;

public interface PartService {
    void seedData() throws IOException;
    Part getRandomPart();
}
