package com.example.cardealer.service;

import com.example.cardealer.model.entity.Supplier;

import java.io.IOException;

public interface SupplierService {
    void seedData() throws IOException;
    Supplier getRandomSupplier();
}
