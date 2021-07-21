package com.example.cardealer;

import com.example.cardealer.model.dto.CarsToyotaDto;
import com.example.cardealer.model.dto.CustomerOrderedCapitalDto;
import com.example.cardealer.model.dto.LocalSuppliersDto;
import com.example.cardealer.service.*;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static com.example.cardealer.constants.GlobalApplicationConstants.FILE_PATH_OUT;
import static com.example.cardealer.constants.GlobalApplicationConstants.FILE_PATH_READ;

@Component
public class MainCarDealer implements CommandLineRunner {

    private final BufferedReader reader;
    private final Gson gson;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    public MainCarDealer(Gson gson, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.gson = gson;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        while (true) {
            System.out.println("Enter task number:");
            int taskNumber = Integer.parseInt(reader.readLine());
            switch (taskNumber) {
                case 1 -> orderedCustomers();
                case 2 -> carsFromMakeToyota();
                case 3 -> localSuppliers();

            }
        }

    }

    private void localSuppliers() {
        List<LocalSuppliersDto> localSuppliersDtos = supplierService.getLocalSuppliers();
    }

    private void carsFromMakeToyota() throws IOException {
        List<CarsToyotaDto> carsToyotaDtos = carService.getCarsFromMakeToyota("Toyota");
        String content = gson.toJson(carsToyotaDtos);
        writeToFile((FILE_PATH_OUT + "toyota-cars.json"), content);
    }


    private void orderedCustomers() throws IOException {
        List<CustomerOrderedCapitalDto> customerOrderedCapitalDtos = this.customerService.getOrderedCustomers();
        String content = gson.toJson(customerOrderedCapitalDtos);
        writeToFile(FILE_PATH_OUT + "ordered-customers.json", content);
    }

    private void writeToFile(String path, String content) throws IOException {
        Files.write(Path.of(path), Collections.singleton(content));
    }


    private void seedData() throws IOException {
        this.supplierService.seedData();
        this.partService.seedData();
        this.carService.seedData();
        this.customerService.seedData();
        this.saleService.seedData();


    }


}
