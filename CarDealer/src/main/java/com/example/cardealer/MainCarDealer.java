package com.example.cardealer;

import com.example.cardealer.model.dto.*;
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
            System.out.println("Enter task number from 1 to 6:");
            int taskNumber = Integer.parseInt(reader.readLine());
            switch (taskNumber) {
                case 1 -> orderedCustomers();
                case 2 -> carsFromMakeToyota();
                case 3 -> localSuppliers();
                case 4 -> carsAndParts();
                case 5 -> customerTotalSales();
                case 6 -> salesWithAppliedDiscount();
                default -> System.err.println("Invalid task number!");

            }
        }

    }

    private void salesWithAppliedDiscount() throws IOException {
     List<SaleDiscountCustomerDto> saleDiscountCustomerDtos = saleService.getAllSalesWithDiscount();
        String content = gson.toJson(saleDiscountCustomerDtos);
        writeToFile((FILE_PATH_OUT + "sales-discounts.json"), content);
    }

    private void customerTotalSales() throws IOException {
        List<CustomerTotalSalesDto> customerTotalSalesDtos = customerService.getCustomersWithTheirSales();
        String content = gson.toJson(customerTotalSalesDtos);
        writeToFile((FILE_PATH_OUT + "customers-total-sales.json"), content);
    }

    private void carsAndParts() throws IOException {
        List<CarsAndParts> carsAndPartsDtos = carService.getCarsAndParts();
        String content = gson.toJson(carsAndPartsDtos);
        writeToFile((FILE_PATH_OUT + "cars-and-parts.json"), content);
    }

    private void localSuppliers() throws IOException {
        List<LocalSuppliersDto> localSuppliersDtos = supplierService.getLocalSuppliers();
        String content = gson.toJson(localSuppliersDtos);
        writeToFile((FILE_PATH_OUT + "local-suppliers.json"), content);
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
