package com.example.cardealer.service.impl;

import com.example.cardealer.constants.GlobalApplicationConstants;
import com.example.cardealer.model.dto.LocalSuppliersDto;
import com.example.cardealer.model.dto.SupplierSeedDto;
import com.example.cardealer.model.entity.Supplier;
import com.example.cardealer.repository.SupplierRepository;
import com.example.cardealer.service.SupplierService;
import com.example.cardealer.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedData() throws IOException {
        if (this.supplierRepository.count() > 0) {
            return;
        }

        String data = Files.readString(Path.of(GlobalApplicationConstants.FILE_PATH_READ + "suppliers.json"));

        SupplierSeedDto[] supplierSeedDtos = gson.fromJson(data, SupplierSeedDto[].class);
        Arrays.stream(supplierSeedDtos)
                .filter(validationUtil::isValid)
                .map(supplierSeedDto -> modelMapper
                        .map(supplierSeedDto, Supplier.class))
                .forEach(supplierRepository::save);

    }

    @Override
    public Supplier getRandomSupplier() {
        long count = this.supplierRepository.count();

        long randomId = ThreadLocalRandom.current().nextLong(1, count + 1);

        return this.supplierRepository.getById(randomId);
    }

    @Override
    public List<LocalSuppliersDto> getLocalSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAllByIsImporterIsFalse();
        List<LocalSuppliersDto> localSuppliersDtos = suppliers.stream()
                .map(supplier -> {
                    LocalSuppliersDto localSuppliersDto = modelMapper.map(supplier, LocalSuppliersDto.class);
                    localSuppliersDto.setPartsCount(supplier.getParts().size());
                    return localSuppliersDto;
                })
                .collect(Collectors.toList());

        return localSuppliersDtos;
    }
}
