package com.example.cardealer.service;

import com.example.cardealer.model.dto.LocalSuppliersDto;
import com.example.cardealer.model.entity.Supplier;

import java.io.IOException;
import java.util.List;

public interface SupplierService {
    void seedData() throws IOException;
    Supplier getRandomSupplier();

    List<LocalSuppliersDto> getLocalSuppliers();

}
